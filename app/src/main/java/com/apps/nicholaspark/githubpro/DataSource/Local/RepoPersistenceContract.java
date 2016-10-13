package com.apps.nicholaspark.githubpro.DataSource.Local;

import android.provider.BaseColumns;

/**
 * Created by nicholaspark on 10/12/16.
 */

public final class RepoPersistenceContract {

    //Create a private constructor so one can instantiate this class
    //This class was meant solely for definition purposes
    private RepoPersistenceContract(){}


    public static abstract class RepoEntry implements BaseColumns{
        public static final String TABLE_NAME = "repos";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_FULL_NAME = "full_name";
        public static final String COLUMN_NAME_OWNER = "owner";
        public static final String COLUMN_NAME_PRIVATE = "private";
        public static final String COLUMN_NAME_HTML_URL = "html_url";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_FORK = "fork";
        public static final String COLUMN_NAME_URL = "url";
    }
}
