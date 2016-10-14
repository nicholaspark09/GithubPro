package com.apps.nicholaspark.githubpro.RepoDetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apps.nicholaspark.githubpro.Models.Repo;
import com.apps.nicholaspark.githubpro.R;
import com.apps.nicholaspark.githubpro.RepoEdit.RepoEditActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RepoDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RepoDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepoDetailFragment extends Fragment implements RepoDetailContract.View{

    private static final String ARG_PARAM1 = "param1";
    private RepoDetailContract.Presenter presenter;

    private static final String TAG = RepoDetailFragment.class.getSimpleName();
    private String mParam1;
    TextView descriptionTextView;


    private OnFragmentInteractionListener mListener;

    public RepoDetailFragment() {
        // Required empty public constructor
    }


    public static RepoDetailFragment newInstance(String param1, String param2) {
        RepoDetailFragment fragment = new RepoDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_detail, container, false);

        descriptionTextView = (TextView)view.findViewById(R.id.descriptionTextView);


        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteRepo();
            }
        });

        Button editButton = (Button)view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editRepo();
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
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(active)
            Log.d(TAG,"Loading...");
        else
            Log.d(TAG,"Not loading");
    }

    @Override
    public void showTitle(String title) {
        getActivity().setTitle(title);
    }

    @Override
    public void showDescription(String description) {
        descriptionTextView.setText(description);
    }

    @Override
    public void showEditRepo(int repoId) {
        Intent intent = new Intent(getActivity(), RepoEditActivity.class);
        intent.putExtra("repoId",repoId);
        startActivity(intent);
    }

    @Override
    public void showRepoDeleted() {
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(RepoDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
