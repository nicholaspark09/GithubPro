package com.apps.nicholaspark.githubpro.RepoEdit;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.nicholaspark.githubpro.GithubPro;
import com.apps.nicholaspark.githubpro.R;

import javax.inject.Inject;

public class RepoEditActivity extends AppCompatActivity implements RepoEditFragment.OnFragmentInteractionListener{

    @Inject
    RepoEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_edit);

        int repoId = getIntent().getIntExtra("repoId",-1);
        RepoEditFragment fragment = RepoEditFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,fragment).commit();

        DaggerRepoEditComponent.builder()
                .reposComponent(((GithubPro)getApplication()).getReposComponent())
                .repoEditPresenterModule(new RepoEditPresenterModule(fragment,repoId))
                .build()
                .inject(this);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
