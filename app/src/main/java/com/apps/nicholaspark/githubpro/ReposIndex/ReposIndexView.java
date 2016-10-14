package com.apps.nicholaspark.githubpro.ReposIndex;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.nicholaspark.githubpro.Models.Repo;
import com.apps.nicholaspark.githubpro.R;
import com.apps.nicholaspark.githubpro.RepoDetail.RepoDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReposIndexView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReposIndexView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReposIndexView extends Fragment implements ReposIndexContract.View{

    private static final String TAG = ReposIndexView.class.getSimpleName();

    //The presenter will get all the data from the model for you
    ReposIndexContract.Presenter presenter;
    SwipeRefreshLayout swipeLayout;
    RecyclerView recyclerView;
    ReposIndexAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public ReposIndexView() {
        // Required empty public constructor
    }

    public static ReposIndexView newInstance() {
        ReposIndexView fragment = new ReposIndexView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repos_index_view, container, false);


        swipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeLayout);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        adapter = new ReposIndexAdapter(new ArrayList<Repo>(0),mItemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadRepos(true);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showRepos(List<Repo> repos) {
        Log.d(TAG,"Size of repos: "+repos.size());
        adapter.replaceData(repos);
    }

    @Override
    public void showAddRepo() {

    }

    @Override
    public void showRepoDetailsUi(int repoId) {
        Intent intent = new Intent(getContext(), RepoDetailActivity.class);
        intent.putExtra("repoId",repoId);
        startActivity(intent);
    }

    @Override
    public void showNoRepos() {

    }

    @Override
    public void showSuccessfullySaved() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(@NonNull ReposIndexContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    /**
     *      Listen for clicks on Repos in the RecyclerViews
     *
     */
    RepoItemListener mItemListener = new RepoItemListener() {
        @Override
        public void onRepoClick(Repo repo) {
            presenter.openRepoDetails(repo);
        }
    };

    public class ReposIndexAdapter extends RecyclerView.Adapter<ReposIndexAdapter.ViewHolder>
    {

        List<Repo> repos;
        private RepoItemListener listener;

        public ReposIndexAdapter(List<Repo> repos, RepoItemListener listener){
            this.listener = listener;
            this.repos = repos;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_row,parent,false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Repo repo = repos.get(position);
            holder.titleTextView.setText(repo.getName());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onRepoClick(repo);
                }
            });
        }

        @Override
        public int getItemCount() {
            return repos.size();
        }

        public void replaceData(List<Repo> repos){
            this.repos = repos;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public View view;
            public TextView titleTextView;

            public ViewHolder(View view){
                super(view);
                this.view = view;
                titleTextView = (TextView)view.findViewById(R.id.titleTextView);
            }
        }

    }

    //Contract to determine item events
    public interface RepoItemListener{
        void onRepoClick(Repo repo);
    }
}
