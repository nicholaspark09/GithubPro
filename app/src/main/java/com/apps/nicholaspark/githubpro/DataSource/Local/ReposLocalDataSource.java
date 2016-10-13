package com.apps.nicholaspark.githubpro.DataSource.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.apps.nicholaspark.githubpro.DataSource.ReposDataSource;
import com.apps.nicholaspark.githubpro.Models.Repo;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nicholaspark on 10/12/16.
 */

public class ReposLocalDataSource implements ReposDataSource{

    @NonNull
    private BriteDatabase briteDatabase;

    @NonNull final RepoDbHelper helper;

    @NonNull
    private Func1<Cursor,Repo> mRepoMapperFunction;

    //You should inject the db
    @Inject
    public ReposLocalDataSource(@NonNull RepoDbHelper helper, @NonNull Scheduler scheduler){
        this.helper = helper;
        SqlBrite sqlBrite = SqlBrite.create();
        briteDatabase = sqlBrite.wrapDatabaseHelper(helper, scheduler);
        mRepoMapperFunction = new Func1<Cursor, Repo>() {
            @Override
            public Repo call(Cursor cursor) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(RepoPersistenceContract.RepoEntry.COLUMN_NAME_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(RepoPersistenceContract.RepoEntry.COLUMN_NAME_NAME));
                String fullname = cursor.getString(cursor.getColumnIndexOrThrow(RepoPersistenceContract.RepoEntry.COLUMN_NAME_FULL_NAME));
                int priv = cursor.getInt(cursor.getColumnIndexOrThrow(RepoPersistenceContract.RepoEntry.COLUMN_NAME_PRIVATE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(RepoPersistenceContract.RepoEntry.COLUMN_NAME_DESCRIPTION));
                String html = cursor.getString(cursor.getColumnIndexOrThrow(RepoPersistenceContract.RepoEntry.COLUMN_NAME_HTML_URL));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(RepoPersistenceContract.RepoEntry.COLUMN_NAME_URL));
                Repo repo = new Repo();
                repo.setId(id);
                repo.setName(name);
                repo.setFullName(fullname);
                if(priv == 1)
                    repo.setPrivate(true);
                else
                    repo.setPrivate(false);
                repo.setDescription(description);
                repo.setHtmlUrl(html);
                repo.setUrl(url);
                return repo;
            }
        };
    }



    @Override
    public Observable<List<Repo>> getRepos(String username) {


        String[] projection = {
                RepoPersistenceContract.RepoEntry.COLUMN_NAME_ID,
                RepoPersistenceContract.RepoEntry.COLUMN_NAME_NAME,
                RepoPersistenceContract.RepoEntry.COLUMN_NAME_FULL_NAME,
                RepoPersistenceContract.RepoEntry.COLUMN_NAME_PRIVATE,
                RepoPersistenceContract.RepoEntry.COLUMN_NAME_DESCRIPTION,
                RepoPersistenceContract.RepoEntry.COLUMN_NAME_HTML_URL,
                RepoPersistenceContract.RepoEntry.COLUMN_NAME_URL
        };
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",",projection), RepoPersistenceContract.RepoEntry.TABLE_NAME);
        return briteDatabase.createQuery(RepoPersistenceContract.RepoEntry.TABLE_NAME,sql)
                .mapToList(mRepoMapperFunction);
    }

    @Override
    public void saveRepo(@NonNull Repo repo) {
        ContentValues values = new ContentValues();
        values.put(RepoPersistenceContract.RepoEntry.COLUMN_NAME_ID,repo.getId());
        values.put(RepoPersistenceContract.RepoEntry.COLUMN_NAME_NAME,repo.getName());
        values.put(RepoPersistenceContract.RepoEntry.COLUMN_NAME_FULL_NAME,repo.getFullName());
        values.put(RepoPersistenceContract.RepoEntry.COLUMN_NAME_PRIVATE,repo.getPrivate());
        values.put(RepoPersistenceContract.RepoEntry.COLUMN_NAME_DESCRIPTION,repo.getDescription());
        values.put(RepoPersistenceContract.RepoEntry.COLUMN_NAME_HTML_URL,repo.getHtmlUrl());
        values.put(RepoPersistenceContract.RepoEntry.COLUMN_NAME_URL,repo.getUrl());
        briteDatabase.insert(RepoPersistenceContract.RepoEntry.TABLE_NAME,values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void deleteRepo(@NonNull int repoId) {
        String selection = RepoPersistenceContract.RepoEntry.COLUMN_NAME_ID+" LIKE ?";
        String[] args = {String.valueOf(repoId)};
        briteDatabase.delete(RepoPersistenceContract.RepoEntry.TABLE_NAME,selection,args);
    }

    @Override
    public void deleteAllRepos() {
        briteDatabase.delete(RepoPersistenceContract.RepoEntry.TABLE_NAME,null);
    }

    @Override
    public void refreshRepos() {
        //The Repository class should handle this logic, not the localdatasource
    }


}
