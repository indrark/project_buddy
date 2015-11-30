package com.njit.buddy.app;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.njit.buddy.app.network.Connector;

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
            getSupportActionBar().setCustomView(R.layout.abs_back);

            getSupportActionBar().getCustomView().findViewById(R.id.btn_back).setOnClickListener(btn_back_click_listener);
            TextView tv_title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.tv_title);
            tv_title.setText(getResources().getString(R.string.title_activity_account));
        }
        Button btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(btn_logout_click_listener);
    }

    private void gotoBuddyActivity() {
        Intent intent = new Intent(this, BuddyActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("buddy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(getResources().getString(R.string.key_token));
        editor.remove(getResources().getString(R.string.key_tab));
        editor.remove(getResources().getString(R.string.key_uid));
        editor.apply();
        Connector.setAuthenticationToken(null);
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private View.OnClickListener btn_back_click_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            gotoBuddyActivity();
        }

    };

    private View.OnClickListener btn_logout_click_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            logout();
        }

    };

}
