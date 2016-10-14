package com.apps.nicholaspark.githubpro.RepoDetail;

import com.apps.nicholaspark.githubpro.BasePresenter;
import com.apps.nicholaspark.githubpro.BaseView;
import com.apps.nicholaspark.githubpro.Models.Repo;

/**
 * Created by nicholaspark on 10/13/16.
 */

public interface RepoDetailContract {

    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showTitle(String title);
        void showDescription(String description);
        void showEditRepo(int repoId);
        void showRepoDeleted();
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        void editRepo();
        void deleteRepo();
        void openRepo();
    }

}
