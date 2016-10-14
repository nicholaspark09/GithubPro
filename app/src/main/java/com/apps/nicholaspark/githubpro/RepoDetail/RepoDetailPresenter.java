package com.apps.nicholaspark.githubpro.RepoDetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.apps.nicholaspark.githubpro.DataSource.ReposRepository;
import com.apps.nicholaspark.githubpro.Models.Repo;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nicholaspark on 10/13/16.
 */

final class RepoDetailPresenter implements RepoDetailContract.Presenter{

    private static final String TAG = RepoDetailPresenter.class.getSimpleName();
    private ReposRepository mReposRepository;
    private CompositeSubscription mSubscription;
    private RepoDetailContract.View mView;

    private int repoId;

    @Inject
    public RepoDetailPresenter(@NonNull int repoId,RepoDetailContract.View view,@NonNull ReposRepository repository,@NonNull CompositeSubscription subscription){
        mView = view;
        mReposRepository = repository;
        mSubscription = subscription;
        this.repoId = repoId;
    }

    @Inject
    public void setupListeners(){
        mView.setPresenter(this);
    }


    @Override
    public void editRepo() {
        mView.showEditRepo(repoId);
    }

    @Override
    public void deleteRepo() {
        mReposRepository.deleteRepo(repoId);
        mView.showRepoDeleted();
    }

    @Override
    public void openRepo() {
        mView.setLoadingIndicator(true);
        Subscription subscription = mReposRepository.getRepo(repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Repo>() {
                    @Override
                    public void onCompleted() {
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"Error: "+e.getMessage());
                    }

                    @Override
                    public void onNext(Repo repo) {
                        showRepo(repo);
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void subscribe() {
        openRepo();
    }

    @Override
    public void unsubscribe() {
        mSubscription.clear();
    }

    private void showRepo(Repo repo){
        mView.showTitle(repo.getName());
        mView.showDescription(repo.getDescription());
    }
}
