package com.apps.nicholaspark.githubpro.RepoEdit;

import android.support.annotation.NonNull;
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
 * Created by nicholaspark on 10/14/16.
 */

final class RepoEditPresenter implements RepoEditContract.Presenter{

    private static final String TAG = RepoEditPresenter.class.getSimpleName();
    private ReposRepository mReposRepository;
    private RepoEditContract.View mView;
    private int repoId;
    private CompositeSubscription mSubscriptions;

    @Inject
    public RepoEditPresenter(@NonNull ReposRepository repository, @NonNull RepoEditContract.View view, @NonNull int repoId, @NonNull CompositeSubscription subscription){
        mReposRepository = repository;
        mView = view;
        this.repoId = repoId;
        mSubscriptions = subscription;
    }

    @Inject
    public void setupListeners(){
        mView.setPresenter(this);
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
                        mView.setLoadingIndicator(false);
                        Log.d(TAG,"Error: "+e.getMessage());
                    }

                    @Override
                    public void onNext(Repo repo) {
                        showRepo(repo);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void saveRepo(Repo repo) {

    }

    @Override
    public void deleteRepo() {

    }

    @Override
    public void subscribe() {
        openRepo();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    private void showRepo(Repo repo){
        mView.showTitle(repo.getName());
        mView.showDescription(repo.getDescription());
    }


}
