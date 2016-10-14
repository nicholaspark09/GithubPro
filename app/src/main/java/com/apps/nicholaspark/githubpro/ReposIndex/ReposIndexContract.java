package com.apps.nicholaspark.githubpro.ReposIndex;

import android.support.annotation.NonNull;

import com.apps.nicholaspark.githubpro.BasePresenter;
import com.apps.nicholaspark.githubpro.BaseView;
import com.apps.nicholaspark.githubpro.Models.Repo;

import java.util.List;

/**
 * Created by nicholaspark on 10/12/16.
 */

public interface ReposIndexContract {


    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showRepos(List<Repo> repos);
        void showAddRepo();
        void showRepoDetailsUi(int repoId);
        void showNoRepos();
        void showSuccessfullySaved();
        void showLoadingReposError();
        boolean isActive();

    }


    interface Presenter extends BasePresenter{

        void result(int requestCode, int resultCode);
        void loadRepos(boolean forceUpdate);
        void addNewRepo();
        void openRepoDetails(@NonNull Repo repo);
        void deleteAllRepos();
        void deleteRepo(@NonNull Repo repo);
    }
}
