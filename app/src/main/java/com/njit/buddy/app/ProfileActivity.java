package com.njit.buddy.app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by toyknight on 8/16/2015.
 */
public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initComponents();
    }

    @SuppressWarnings("ResourceType")
    private void initComponents() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_profile);

        getSupportActionBar().getCustomView().findViewById(R.id.btn_profile_back).setOnClickListener(btn_back_click_listener);
        getSupportActionBar().getCustomView().findViewById(R.id.btn_profile_edit).setOnClickListener(btn_edit_click_listener);
    }

    private View.OnClickListener btn_edit_click_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), ProfileEditActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }

    };

    private View.OnClickListener btn_back_click_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), BuddyActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }

    };

}
