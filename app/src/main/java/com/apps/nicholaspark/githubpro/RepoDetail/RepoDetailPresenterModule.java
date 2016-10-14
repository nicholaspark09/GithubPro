package com.apps.nicholaspark.githubpro.RepoDetail;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nicholaspark on 10/14/16.
 */
@Module
public class RepoDetailPresenterModule {

    private RepoDetailContract.View mView;
    private int repoId;

    public RepoDetailPresenterModule(RepoDetailContract.View view, int repoId){
        mView = view;
        this.repoId = repoId;
    }

    @Provides
    RepoDetailContract.View providesRepoDetailView(){
        return mView;
    }

    @Provides
    int providesRepoId(){
        return repoId;
    }

    @Provides
    CompositeSubscription providesSubscription(){
        return new CompositeSubscription();
    }

}
