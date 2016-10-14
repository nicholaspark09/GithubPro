package com.apps.nicholaspark.githubpro;

import com.apps.nicholaspark.githubpro.DataSource.ReposRepository;
import com.apps.nicholaspark.githubpro.Models.Repo;
import com.apps.nicholaspark.githubpro.ReposIndex.ReposIndexContract;
import com.apps.nicholaspark.githubpro.ReposIndex.ReposIndexPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.subscriptions.CompositeSubscription;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nicholaspark on 10/14/16.
 */

public class ReposIndexPresenterTest {

    private static List<Repo> REPOS;

    private ReposIndexPresenter reposIndexPresenter;

    private static final String username = "nicholaspark09";

    @Mock
    private ReposRepository mReposRepository;

    @Mock
    private ReposIndexContract.View mView;

    @Mock
    private Scheduler androidMainThread;

    private CompositeSubscription subscription;


    @Before
    public void setupReposPresenterTest(){
        MockitoAnnotations.initMocks(this);

        subscription = new CompositeSubscription();
        reposIndexPresenter = new ReposIndexPresenter(mReposRepository,mView,androidMainThread);
        REPOS = new ArrayList<>();
        Repo repo = new Repo();
        repo.setId(1);
        repo.setName("REPO_TEST_1");
        Repo repoTwo = new Repo();
        repoTwo.setId(2);
        repoTwo.setName("REPO_TEST_2");
        REPOS.add(repo);
        REPOS.add(repoTwo);
    }

    @Test
    public void loadAllReposAndLoadIntoView(){
        when(mReposRepository.getRepos(username)).thenReturn(Observable.just(REPOS));
        reposIndexPresenter.loadRepos(true);
        verify(mView).setLoadingIndicator(true);
    }

    @Test
    public void clickOnRepo_ShowsRepoUiDetails(){
        Repo repo = new Repo();
        repo.setId(1);
        repo.setName("Brand New Repo");
        reposIndexPresenter.openRepoDetails(repo);
        verify(mView).showRepoDetailsUi(1);
    }

    @Test
    public void errorLoadingRepos_ShowsError(){
        //No repos available, throw exception back
        when(mReposRepository.getRepos(username)).thenReturn(Observable.<List<Repo>>error(new Exception()));

        reposIndexPresenter.loadRepos(true);

        //Verify that an error message was shown
        verify(mView).showLoadingReposError();
    }


}
