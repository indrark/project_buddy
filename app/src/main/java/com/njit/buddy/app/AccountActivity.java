package com.njit.buddy.app;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author toyknight 8/16/2015.
 */
public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initComponents();
    }

    @SuppressWarnings("ResourceType")
    private void initComponents() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.abs_account);

            getSupportActionBar().getCustomView().
                    findViewById(R.id.btn_account_back).setOnClickListener(btn_back_click_listener);
            findViewById(R.id.btn_logout).setOnClickListener(btn_logout_click_listener);
        }
    }

    private View.OnClickListener btn_back_click_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        }

    };

    private View.OnClickListener btn_logout_click_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AccountActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("token");
            editor.apply();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }

    };

}
