package com.njit.buddy.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.njit.buddy.app.R;

/**
 * @author toyknight 8/15/2015.
 */
public class AttentionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View news_layout = inflater.inflate(R.layout.fragment_attention, container, false);
        return news_layout;
    }

}
