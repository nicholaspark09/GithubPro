package com.apps.nicholaspark.githubpro.Modules;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nicholaspark on 10/12/16.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    Context providesContext(){
        return context;
    }


}
