package com.apps.nicholaspark.githubpro.DataSource;

import android.content.Context;

import com.apps.nicholaspark.githubpro.Clients.GithubApiService;
import com.apps.nicholaspark.githubpro.DataSource.Local.Local;
import com.apps.nicholaspark.githubpro.DataSource.Local.RepoDbHelper;
import com.apps.nicholaspark.githubpro.DataSource.Local.ReposLocalDataSource;
import com.apps.nicholaspark.githubpro.DataSource.Remote.Remote;
import com.apps.nicholaspark.githubpro.DataSource.Remote.ReposRemoteDataSource;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nicholaspark on 10/12/16.
 */
@Module
public class ReposRepositoryModule {

    @Singleton
    @Provides
    GithubApiService providesGithubService(Context context){
        return new GithubApiService.CreatorFactory().makeGithubApiService(context);
    }

    @Singleton
    @Provides
    RepoDbHelper providesDbHelper(Context context){
        return new RepoDbHelper(context);
    }

    @Singleton
    @Provides
    Scheduler providesIoScheduler(){
        return Schedulers.io();
    }

    @Singleton
    @Provides
    @Local
    @Named("localSource")
    ReposDataSource providesLocalDataSource(RepoDbHelper helper, Scheduler ioScheduler){
        return new ReposLocalDataSource(helper, ioScheduler);
    }

    @Singleton
    @Provides
    @Remote
    @Named("remoteSource")
    ReposDataSource providesRemoteDataSource(GithubApiService apiService){
        return new ReposRemoteDataSource(apiService);
    }

    @Singleton
    @Provides
    CompositeSubscription providesCompositeSubscription(){
        return new CompositeSubscription();
    }



}
