package com.apps.nicholaspark.githubpro.DataSource.Local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nicholaspark on 10/12/16.
 */

public class RepoDbHelper extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "findrepos.db";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE "+RepoPersistenceContract.RepoEntry.TABLE_NAME+"("
            +RepoPersistenceContract.RepoEntry.COLUMN_NAME_ID+" integer primary key,"
                    +RepoPersistenceContract.RepoEntry.COLUMN_NAME_NAME+" text,"
            +RepoPersistenceContract.RepoEntry.COLUMN_NAME_FULL_NAME+" text,"
            +RepoPersistenceContract.RepoEntry.COLUMN_NAME_OWNER+" text,"
            +RepoPersistenceContract.RepoEntry.COLUMN_NAME_PRIVATE+" boolean,"
            +RepoPersistenceContract.RepoEntry.COLUMN_NAME_HTML_URL+" text,"
            +RepoPersistenceContract.RepoEntry.COLUMN_NAME_DESCRIPTION+" text,"
            +RepoPersistenceContract.RepoEntry.COLUMN_NAME_FORK+" text,"
            +RepoPersistenceContract.RepoEntry.COLUMN_NAME_URL+" text)";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF NOT EXISTS "+RepoPersistenceContract.RepoEntry.TABLE_NAME;


    public RepoDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }
}
