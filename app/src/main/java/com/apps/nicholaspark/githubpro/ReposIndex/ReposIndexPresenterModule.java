package com.apps.nicholaspark.githubpro.ReposIndex;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nicholaspark on 10/13/16.
 */
@Module
public class ReposIndexPresenterModule {

    private final ReposIndexContract.View mView;

    public ReposIndexPresenterModule(ReposIndexContract.View view){
        mView = view;
    }

    @Provides
    ReposIndexContract.View providesReposContractView(){
        return mView;
    }

    @Provides
    Scheduler providesAndroidScheduler(){
        return AndroidSchedulers.mainThread();
    }
}
