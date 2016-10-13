package com.apps.nicholaspark.githubpro.ReposIndex;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.nicholaspark.githubpro.GithubPro;
import com.apps.nicholaspark.githubpro.R;

import javax.inject.Inject;

public class ReposIndexActivity extends AppCompatActivity implements ReposIndexView.OnFragmentInteractionListener{

    @Inject
    ReposIndexPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos_index);


        ReposIndexView fragment = ReposIndexView.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,fragment,"ReposIndexTag").commit();

        DaggerReposIndexComponent.builder()
                .reposComponent(((GithubPro)getApplication()).getReposComponent())
                .reposIndexPresenterModule(new ReposIndexPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
