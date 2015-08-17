package com.njit.buddy.app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by toyknight on 8/16/2015.
 */
public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initComponents();
    }

    @SuppressWarnings("ResourceType")
    private void initComponents() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_setting);

        getSupportActionBar().getCustomView().findViewById(R.id.btn_setting_back).setOnClickListener(btn_back_click_listener);
    }

    private View.OnClickListener btn_back_click_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), BuddyActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }

    };

}
