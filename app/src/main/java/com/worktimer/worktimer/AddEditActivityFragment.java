package com.worktimer.worktimer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditActivityFragment extends Fragment /* implements View.OnClickListener */{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "AddEditActivityFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Bundle mArgs = new Bundle();
    private static final int LOADER_ID = 1;


    EditText name;
    EditText description;
    EditText sortOrd;

    private OnFragmentInteractionListener mListener;

    public AddEditActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEditActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditActivityFragment newInstance(String param1, String param2) {
        AddEditActivityFragment fragment = new AddEditActivityFragment();
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
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_add_edit_activity, container, false);
        Button saveBtn = view.findViewById(R.id.addedit_save);
   //     saveBtn.setOnClickListener(this);

        name = view.findViewById(R.id.addedit_name);
        description = view.findViewById(R.id.addedit_description);
        sortOrd = view.findViewById(R.id.addedit_sortorder);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");

                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();

                values.put(WorkTimer.Columns.TASK_NAME, name.getText().toString());
                values.put(WorkTimer.Columns.TASK_DESCRIPTION, description.getText().toString());
                values.put(WorkTimer.Columns.TASKS_SORTORDER, 1);
                contentResolver.insert(WorkTimer.CONTENT_URI, values);

                if(mListener != null) {
                    mListener.onFragmentInteraction(null);
                }
                //android.support.v4.app.LoaderManager.getInstance(getActivity()).restartLoader(LOADER_ID, mArgs, null);
            }
        });

        return view;
        //return inflater.inflate(R.layout.fragment_add_edit_activity, container, false);
    }
/*
    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
    }
*/
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
/*        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */

        // Activities containing this fragment must implement it's callbacks.
        Activity activity = getActivity();
        if(!(activity instanceof OnFragmentInteractionListener)) {
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement AddEditActivityFragment.OnSaveClicked interface");
        }
        mListener = (OnFragmentInteractionListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
