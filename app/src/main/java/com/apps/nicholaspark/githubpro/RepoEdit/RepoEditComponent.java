package com.apps.nicholaspark.githubpro.RepoEdit;

import com.apps.nicholaspark.githubpro.DataSource.ReposComponent;
import com.apps.nicholaspark.githubpro.DataSource.ReposRepository;
import com.apps.nicholaspark.githubpro.Utils.FragmentScoped;

import dagger.Component;

/**
 * Created by nicholaspark on 10/14/16.
 */
@FragmentScoped
@Component(dependencies = ReposComponent.class, modules = RepoEditPresenterModule.class)
public interface RepoEditComponent {
    void inject(RepoEditActivity activity);
}
