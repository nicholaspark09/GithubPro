package com.apps.nicholaspark.githubpro.ReposIndex;

import android.support.annotation.NonNull;
import android.util.Log;

import com.apps.nicholaspark.githubpro.DataSource.ReposRepository;
import com.apps.nicholaspark.githubpro.Models.Repo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nicholaspark on 10/12/16.
 */

final class ReposIndexPresenter implements ReposIndexContract.Presenter{

    private final ReposRepository mRepository;
    private final ReposIndexContract.View mView;
    private final CompositeSubscription subscriptions;
    private boolean mFirstLoad = true;
    private static final String username = "nicholaspark09";
    private static final String TAG = ReposIndexPresenter.class.getSimpleName();

    @Inject
    ReposIndexPresenter(ReposRepository reposRepository, ReposIndexContract.View view, CompositeSubscription subscription){
        mRepository = reposRepository;
        mView = view;
        this.subscriptions = subscription;
    }

    @Inject
    void setupListeners(){
        mView.setPresenter(this);
    }


    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadRepos(boolean forceUpdate) {
        subscriptions.clear();
        mFirstLoad = false;
        mView.setLoadingIndicator(true);
        if(forceUpdate)
            mRepository.refreshRepos();
        Subscription subscription = mRepository
                .getRepos(username)
                .flatMap(new Func1<List<Repo>, Observable<Repo>>() {
                    @Override
                    public Observable<Repo> call(List<Repo> repos) {
                        return Observable.from(repos);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        mView.setLoadingIndicator(false);
                        Log.d(TAG,"Completed the repository load");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setLoadingIndicator(false);
                        Log.e(TAG,"Error: "+e.getMessage());

                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        mView.showRepos(repos);
                    }
                });
        subscriptions.add(subscription);
    }

    @Override
    public void addNewRepo() {

    }

    @Override
    public void openRepoDetails(@NonNull Repo repo) {

    }

    @Override
    public void deleteAllRepos() {

    }

    @Override
    public void deleteRepo(@NonNull Repo repo) {

    }

    @Override
    public void subscribe() {
        loadRepos(false);
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }
}
