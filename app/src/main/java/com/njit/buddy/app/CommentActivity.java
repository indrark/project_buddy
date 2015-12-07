package com.njit.buddy.app;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * @author toyknight 12/6/2015.
 */
public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initComponents();
    }

    @SuppressWarnings("ResourceType")
    private void initComponents() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.abs_back);

            getSupportActionBar().getCustomView().findViewById(R.id.btn_back).setOnClickListener(btn_back_click_listener);
            TextView tv_title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.tv_title);
            tv_title.setText(getResources().getString(R.string.title_activity_comment));
        }
    }

    private View.OnClickListener btn_back_click_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            finish();
        }

    };

}
