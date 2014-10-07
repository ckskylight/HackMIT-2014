package com.example.helpme;

import com.parse.ParseObject;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) public class HelpMeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance() {
        Fragment fragment = new HelpMeFragment();
        return fragment;
    }

    public HelpMeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.help_me, container, false);
        return rootView;
    }
    
    
    
}
