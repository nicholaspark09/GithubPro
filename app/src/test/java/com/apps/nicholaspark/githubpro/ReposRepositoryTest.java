package com.apps.nicholaspark.githubpro;

import com.apps.nicholaspark.githubpro.DataSource.ReposDataSource;
import com.apps.nicholaspark.githubpro.DataSource.ReposRepository;
import com.apps.nicholaspark.githubpro.Models.Repo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nicholaspark on 10/13/16.
 */

public class ReposRepositoryTest {

    private static final String username = "nicholaspark09";
    private ReposRepository mReposRepository;
    private List<Repo> FAKE_REPOS;
    private TestSubscriber<List<Repo>> mRepoSubscriber;
    private static final String REPO_TITLE_1 = "Test Repo 1";
    private static final String REPO_TITLE_2 = "Test Repo 2";

    @Mock
    private ReposDataSource mReposLocalDataSource;

    @Mock
    private ReposDataSource mReposRemoteDataSource;

    @Before
    public void setupReposRepository(){
        MockitoAnnotations.initMocks(this);

        FAKE_REPOS = new ArrayList<Repo>();
        Repo repo = new Repo();
        repo.setId(1);
        repo.setName(REPO_TITLE_1);
        Repo repoTwo = new Repo();
        repoTwo.setId(2);
        repoTwo.setName(REPO_TITLE_2);
        FAKE_REPOS.add(repo);
        FAKE_REPOS.add(repoTwo);
        mReposRepository = new ReposRepository(mReposLocalDataSource,mReposRemoteDataSource);
        mRepoSubscriber = new TestSubscriber<>();
    }

    private void setReposNotAvailable(ReposDataSource dataSource){
        when(dataSource.getRepos(username)).thenReturn(Observable.just(Collections.<Repo>emptyList()));
    }

    private void setReposAvailable(ReposDataSource datasource,List<Repo> repos){
        //Don't actually allow the datasource to complete,
        //Fill the datasource with fake preset objects
        when(datasource.getRepos(username)).thenReturn(Observable.just(repos).concatWith(Observable.<List<Repo>>never()));
    }

    @Test
    public void getRepos_repositoryCachesAfterFirstSubscription_whenReposAvailableInLocalStorage(){
        //Preset the repos for the local data source
        setReposAvailable(mReposLocalDataSource,FAKE_REPOS);

        //Make sure the network isn't available
        setReposNotAvailable(mReposRemoteDataSource);

        //When two subscriptions set
        TestSubscriber<List<Repo>> testSubscriberOne = new TestSubscriber<>();
        mReposRepository.getRepos(username).subscribe(testSubscriberOne);

        TestSubscriber<List<Repo>> testSubscriberTwo = new TestSubscriber<>();
        mReposRepository.getRepos(username).subscribe(testSubscriberTwo);

        //Verify that these were only requested once

        verify(mReposRemoteDataSource).getRepos(username);
        verify(mReposLocalDataSource).getRepos(username);

        //Make sure the cacheisDirty is set to false
        assertFalse(mReposRepository.mCacheIsDirty);
        testSubscriberOne.assertValue(FAKE_REPOS);
        testSubscriberTwo.assertValue(FAKE_REPOS);
    }

    @Test
    public void getRepos_requestsAllReposFromLocalDataSource(){

        //Set the data for the local repo in test only
        setReposAvailable(mReposLocalDataSource,FAKE_REPOS);

        //Make the sure the remote does not have them
        setReposNotAvailable(mReposRemoteDataSource);

        mReposRepository.getRepos(username).subscribe(mRepoSubscriber);
        //Verify that the localdatasource getRepos() method was called
        verify(mReposLocalDataSource).getRepos(username);
        //Make sure the subscriber has the same data as the local
        mRepoSubscriber.assertValue(FAKE_REPOS);
    }

    @Test
    public void deleteAllRepos_deletesCache(){
        mReposRepository.saveRepo(FAKE_REPOS.get(0));
        mReposRepository.saveRepo(FAKE_REPOS.get(1));

        //Should have saved 2 repos
        //Delete them all
        mReposRepository.deleteAllRepos();

        //Verify that those methods were called
        verify(mReposRemoteDataSource).deleteAllRepos();
        verify(mReposLocalDataSource).deleteAllRepos();

        assertThat(mReposRepository.mCachedRepos.size(), is(0));
    }

    @Test
    public void saveRepo_savesRepoToLocal(){
        Repo repo = new Repo();
        repo.setId(1);
        repo.setName(REPO_TITLE_1);

        //Save the repo to the repository
        mReposRepository.saveRepo(repo);

        //Verify that this was called in the local repo
        /**
         *      Since you don't save the repo to the server, just verify that the localrepo save method
         *      was called
         */
        verify(mReposLocalDataSource).saveRepo(repo);

        //The cachesize should now be 1
        assertThat(mReposRepository.mCachedRepos.size(),is(1));
    }

    @Test
    public void getRepos_refreshesLocalDataSource(){
        //Make sure the remote has data
        setReposAvailable(mReposRemoteDataSource,FAKE_REPOS);

        //This method should mark the cache as dirty
        mReposRepository.refreshRepos();

        //When you call the getRepos, the remote call should save into local datasource
        mReposRepository.getRepos(username).subscribe(mRepoSubscriber);

        //Verify that it was saved into the local datasource
        verify(mReposLocalDataSource,times(FAKE_REPOS.size())).saveRepo(any(Repo.class));
        mRepoSubscriber.assertValue(FAKE_REPOS);
    }

}
