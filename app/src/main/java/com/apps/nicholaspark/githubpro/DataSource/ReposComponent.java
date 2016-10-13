package com.apps.nicholaspark.githubpro.DataSource;

import com.apps.nicholaspark.githubpro.Modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nicholaspark on 10/12/16.
 */
@Singleton
@Component(modules = {AppModule.class,ReposRepositoryModule.class})
public interface ReposComponent {

    ReposRepository getReposRepository();
}
