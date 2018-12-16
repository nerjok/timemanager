package com.worktimer.worktimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestFragment extends Fragment {

    public static TestFragment newInstance(String text, String text2) {

        TestFragment f = new TestFragment();

        Bundle b = new Bundle();
        b.putString("text", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragments, container, false);

        ((TextView) v.findViewById(R.id.tvFragText)).setText(getArguments().getString("text"));
        return v;
    }
}