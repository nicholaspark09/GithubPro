package com.apps.nicholaspark.githubpro.DataSource.Remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.apps.nicholaspark.githubpro.Clients.GithubApiService;
import com.apps.nicholaspark.githubpro.DataSource.ReposDataSource;
import com.apps.nicholaspark.githubpro.Models.Repo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by nicholaspark on 10/12/16.
 */

public class ReposRemoteDataSource implements ReposDataSource{

    private static final String TAG = ReposRemoteDataSource.class.getSimpleName();
    private GithubApiService apiService;

    //You should inject the client
    @Inject
    public ReposRemoteDataSource(GithubApiService apiService){
        this.apiService = apiService;
    }


    @Override
    public Observable<List<Repo>> getRepos(String username) {
        return apiService.listRepos(username);
    }

    @Override
    public Observable<Repo> getRepo(int repoId) {
        return null;
    }

    @Override
    public void saveRepo(@NonNull Repo repo) {

    }

    @Override
    public void deleteRepo(@NonNull int repoId) {

    }

    @Override
    public void deleteAllRepos() {

    }

    @Override
    public void refreshRepos() {

    }
}
