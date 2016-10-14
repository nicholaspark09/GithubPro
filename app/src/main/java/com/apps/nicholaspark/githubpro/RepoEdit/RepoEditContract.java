package com.apps.nicholaspark.githubpro.RepoEdit;

import com.apps.nicholaspark.githubpro.BasePresenter;
import com.apps.nicholaspark.githubpro.BaseView;
import com.apps.nicholaspark.githubpro.Models.Repo;

/**
 * Created by nicholaspark on 10/14/16.
 */

public interface RepoEditContract {

    interface View extends BaseView<Presenter>{
        void showTitle(String title);
        void showDescription(String description);
        void showSaved();
        void setLoadingIndicator(boolean active);
        void showDeleted();
    }

    interface Presenter extends BasePresenter{
        void openRepo();
        void saveRepo(Repo repo);
        void deleteRepo();
    }
}
