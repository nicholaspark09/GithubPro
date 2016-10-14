package com.apps.nicholaspark.githubpro.RepoEdit;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nicholaspark on 10/14/16.
 */
@Module
public class RepoEditPresenterModule {


    private RepoEditContract.View mView;
    private int repoId;

    public RepoEditPresenterModule(RepoEditContract.View view, int repoId){
        mView = view;
        this.repoId = repoId;
    }

    @Provides
    RepoEditContract.View providesRepoEditContractView(){
        return mView;
    }

    @Provides
    CompositeSubscription providesCompositeSubscription(){
        return new CompositeSubscription();
    }

    @Provides
    int providesRepoId(){
        return repoId;
    }

}
