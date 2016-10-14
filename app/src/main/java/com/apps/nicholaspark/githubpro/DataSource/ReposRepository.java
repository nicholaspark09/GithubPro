package com.apps.nicholaspark.githubpro.DataSource;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.apps.nicholaspark.githubpro.DataSource.Local.Local;
import com.apps.nicholaspark.githubpro.DataSource.Local.ReposLocalDataSource;
import com.apps.nicholaspark.githubpro.DataSource.Remote.Remote;
import com.apps.nicholaspark.githubpro.Models.Repo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nicholaspark on 10/12/16.
 */
@Singleton
public class ReposRepository implements ReposDataSource{

    private static final String TAG = ReposRepository.class.getSimpleName();
    private final ReposDataSource localDataSource;
    private final ReposDataSource remoteDataSource;
    private String username;
    private boolean cacheIsDirty = false;

    @VisibleForTesting
    @Nullable
    public Map<Integer,Repo> mCachedRepos;

    @VisibleForTesting
    public boolean mCacheIsDirty = false;

    @Inject
    public ReposRepository(@Local @Named("localSource") ReposDataSource localDataSource, @Remote @Named("remoteSource") ReposDataSource remoteDataSource){
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<Repo>> getRepos(String username) {
        this.username = username;
        if(mCachedRepos != null && !mCacheIsDirty){
            return Observable.from(mCachedRepos.values()).toList();
        }else if(mCachedRepos == null){
            mCachedRepos = new LinkedHashMap<>();
        }

        Observable<List<Repo>> remoteRepos = getRemoteRepos();
        if (mCacheIsDirty) {
            return remoteRepos;
        }else{
            Observable<List<Repo>> localRepos = getAndCacheLocalRepos();
            return Observable.concat(localRepos,remoteRepos).first();
        }

    }

    @Override
    public Observable<Repo> getRepo(@NonNull final int repoId) {
        return localDataSource.getRepo(repoId);
    }

    private Observable<List<Repo>> getAndCacheLocalRepos(){
        return localDataSource.getRepos(username)
                .flatMap(new Func1<List<Repo>, Observable<List<Repo>>>() {
                    @Override
                    public Observable<List<Repo>> call(List<Repo> repos) {
                        return Observable.from(repos)
                                .doOnNext(new Action1<Repo>() {
                                    @Override
                                    public void call(Repo repo) {
                                        mCachedRepos.put(repo.getId(),repo);
                                    }
                                }).toList();
                    }
                });
    }

    private Observable<List<Repo>> getRemoteRepos(){

        return remoteDataSource
                .getRepos(username)
                .flatMap(new Func1<List<Repo>, Observable<List<Repo>>>() {
                    @Override
                    public Observable<List<Repo>> call(List<Repo> repos) {
                        return Observable.from(repos).doOnNext(new Action1<Repo>() {
                            @Override
                            public void call(Repo repo) {
                                localDataSource.saveRepo(repo);
                                mCachedRepos.put(repo.getId(),repo);
                            }
                        }).toList();
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        Log.d(TAG,"Is completed");
                        cacheIsDirty = false;
                    }
                });

    }



    @Override
    public void saveRepo(@NonNull Repo repo) {

        //Don't actually save it to github as you don't have login yet
        localDataSource.saveRepo(repo);

        //Do in memory cache update
        if(mCachedRepos == null)
            mCachedRepos = new LinkedHashMap<>();

        mCachedRepos.put(repo.getId(),repo);
    }

    @Override
    public void deleteRepo(@NonNull int repoId) {
        localDataSource.deleteRepo(repoId);
        remoteDataSource.deleteRepo(repoId);
        mCachedRepos.remove(repoId);
    }

    @Override
    public void deleteAllRepos() {
        localDataSource.deleteAllRepos();
        remoteDataSource.deleteAllRepos();
        if(mCachedRepos == null){
            mCachedRepos = new LinkedHashMap<>();
        }
        mCachedRepos.clear();
    }

    @Nullable
    private Repo getRepoWithId(@NonNull int repoId){
        return null;
    }



    @Override
    public void refreshRepos() {
        mCacheIsDirty = true;
    }


}
