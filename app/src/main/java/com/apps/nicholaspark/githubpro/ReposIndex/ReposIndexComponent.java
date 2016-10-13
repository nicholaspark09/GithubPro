package com.apps.nicholaspark.githubpro.ReposIndex;

import com.apps.nicholaspark.githubpro.DataSource.ReposComponent;
import com.apps.nicholaspark.githubpro.Utils.FragmentScoped;

import dagger.Component;

/**
 * Created by nicholaspark on 10/13/16.
 */
@FragmentScoped
@Component(dependencies = ReposComponent.class, modules = ReposIndexPresenterModule.class)
public interface ReposIndexComponent {
    void inject(ReposIndexActivity activity);
}
