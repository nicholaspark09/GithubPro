package com.apps.nicholaspark.githubpro.DataSource;

import android.support.annotation.NonNull;

import com.apps.nicholaspark.githubpro.Models.Owner;
import com.apps.nicholaspark.githubpro.Models.Repo;

import java.util.List;

import rx.Observable;

/**
 * Created by nicholaspark on 10/12/16.
 */

public interface ReposDataSource {

    Observable<List<Repo>> getRepos(String username);

    void saveRepo(@NonNull Repo repo);
    void deleteRepo(@NonNull int repoId);
    void deleteAllRepos();
    void refreshRepos();
}
