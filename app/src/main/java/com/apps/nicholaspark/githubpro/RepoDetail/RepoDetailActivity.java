package com.apps.nicholaspark.githubpro.RepoDetail;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.nicholaspark.githubpro.GithubPro;
import com.apps.nicholaspark.githubpro.R;
import com.apps.nicholaspark.githubpro.ReposIndex.DaggerRepoDetailComponent;

import javax.inject.Inject;

public class RepoDetailActivity extends AppCompatActivity implements RepoDetailFragment.OnFragmentInteractionListener{

    @Inject
    RepoDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);

        int repoId = getIntent().getIntExtra("repoId",-1);

        RepoDetailFragment fragment = RepoDetailFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,fragment).commit();

        DaggerRepoDetailComponent.builder()
                .reposComponent(((GithubPro)getApplication()).getReposComponent())
                .repoDetailPresenterModule(new RepoDetailPresenterModule(fragment,repoId))
                .build()
                .inject(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
