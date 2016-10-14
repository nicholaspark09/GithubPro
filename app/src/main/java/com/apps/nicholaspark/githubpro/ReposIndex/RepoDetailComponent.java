package com.apps.nicholaspark.githubpro.ReposIndex;


import com.apps.nicholaspark.githubpro.DataSource.ReposComponent;
import com.apps.nicholaspark.githubpro.RepoDetail.RepoDetailActivity;
import com.apps.nicholaspark.githubpro.RepoDetail.RepoDetailPresenterModule;
import com.apps.nicholaspark.githubpro.Utils.FragmentScoped;

import dagger.Component;

/**
 * Created by nicholaspark on 10/14/16.
 */
@FragmentScoped
@Component(dependencies = ReposComponent.class, modules = RepoDetailPresenterModule.class)
public interface RepoDetailComponent {

    void inject(RepoDetailActivity activity);
}
