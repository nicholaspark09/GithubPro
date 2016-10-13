package com.apps.nicholaspark.githubpro.Clients;

import android.content.Context;

import com.apps.nicholaspark.githubpro.Models.Repo;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by nicholaspark on 10/12/16.
 */

public interface GithubApiService {

    String ENDPOINT = "https://api.github.com";

    @GET("/users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);


    class CreatorFactory{
        public static GithubApiService makeGithubApiService(Context context){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GithubApiService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(GithubApiService.class);
        }
    }
}
