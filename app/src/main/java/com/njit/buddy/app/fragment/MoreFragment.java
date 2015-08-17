package com.njit.buddy.app.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.njit.buddy.app.AccountActivity;
import com.njit.buddy.app.ProfileActivity;
import com.njit.buddy.app.R;
import com.njit.buddy.app.SettingActivity;

/**
 * Created by toyknight on 8/15/2015.
 */
public class MoreFragment extends Fragment implements View.OnClickListener {

    private View btn_account;
    private View btn_profile;
    private View btn_setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View news_layout = inflater.inflate(R.layout.fragment_more, container, false);
        initComponents(news_layout);
        return news_layout;
    }

    private void initComponents(View view) {
        btn_account = view.findViewById(R.id.btn_account);
        btn_profile = view.findViewById(R.id.btn_profile);
        btn_setting = view.findViewById(R.id.btn_setting);

        btn_account.setOnTouchListener(btn_touch_listener);
        btn_account.setOnClickListener(this);
        btn_profile.setOnTouchListener(btn_touch_listener);
        btn_profile.setOnClickListener(this);
        btn_setting.setOnTouchListener(btn_touch_listener);
        btn_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_account:
                startActivity(new Intent(getActivity(), AccountActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.btn_profile:
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.btn_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

    private View.OnTouchListener btn_touch_listener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundResource(R.drawable.border_checked);
                    break;
                case MotionEvent.ACTION_UP:
                    v.setBackgroundResource(R.drawable.border_unchecked);
                    break;
            }
            return false;
        }

    };

}
