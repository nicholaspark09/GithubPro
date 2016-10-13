package com.apps.nicholaspark.githubpro;

import android.app.Application;

import com.apps.nicholaspark.githubpro.DataSource.DaggerReposComponent;
import com.apps.nicholaspark.githubpro.DataSource.ReposComponent;
import com.apps.nicholaspark.githubpro.DataSource.ReposRepositoryModule;
import com.apps.nicholaspark.githubpro.Modules.AppModule;

/**
 * Created by nicholaspark on 10/12/16.
 */

public class GithubPro extends Application {

    private ReposComponent reposComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        reposComponent = DaggerReposComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .reposRepositoryModule(new ReposRepositoryModule())
                .build();

    }

    public ReposComponent getReposComponent(){
        return reposComponent;
    }
}
