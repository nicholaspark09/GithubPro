package com.apps.nicholaspark.githubpro.RepoEdit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.apps.nicholaspark.githubpro.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RepoEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RepoEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepoEditFragment extends Fragment implements RepoEditContract.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RepoEditContract.Presenter presenter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = RepoEditFragment.class.getSimpleName();
    private EditText titleEditText;
    private EditText descriptionEditText;

    private OnFragmentInteractionListener mListener;

    public RepoEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepoEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RepoEditFragment newInstance(String param1, String param2) {
        RepoEditFragment fragment = new RepoEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_edit, container, false);

        titleEditText = (EditText)view.findViewById(R.id.titleEditText);
        descriptionEditText = (EditText)view.findViewById(R.id.descriptionEditText);


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
    public void showTitle(String title) {
        titleEditText.setText(title);
    }

    @Override
    public void showDescription(String description) {
        descriptionEditText.setText(description);
    }

    @Override
    public void showSaved() {

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(active)
            Log.d(TAG,"Loading");
        else
            Log.d(TAG,"Finished loading");
    }

    @Override
    public void showDeleted() {

    }

    @Override
    public void setPresenter(RepoEditContract.Presenter presenter) {
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
}
