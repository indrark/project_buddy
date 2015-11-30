package com.njit.buddy.app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.njit.buddy.app.entity.Hug;
import com.njit.buddy.app.network.task.HugListTask;
import com.njit.buddy.app.util.Log;
import com.njit.buddy.app.widget.HugView;

import java.util.ArrayList;

/**
 * @author toyknight 11/23/2015.
 */
public class HugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug);
        initComponents();
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", -1);
        tryUpdateHugList(pid);
    }

    @SuppressWarnings("ResourceType")
    private void initComponents() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.abs_back);

            getSupportActionBar().getCustomView().findViewById(R.id.btn_back).setOnClickListener(btn_back_click_listener);
            TextView tv_title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.tv_title);
            tv_title.setText(getResources().getString(R.string.title_activity_hug));
        }
    }

    public void tryUpdateHugList(int pid) {
        HugListTask task = new HugListTask() {
            @Override
            public void onSuccess(ArrayList<Hug> result) {
                updateHugList(result);
            }

            @Override
            public void onFail(int error_code) {
                Log.error("List Hug", error_code);
            }
        };
        task.execute(pid, 0, 10);
    }

    public void updateHugList(ArrayList<Hug> hug_list) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.hug_list_layout);
        layout.removeAllViews();
        for (Hug hug : hug_list) {
            HugView post_view = new HugView(this, hug);
            layout.addView(post_view);
        }
    }

    private View.OnClickListener btn_back_click_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            finish();
        }

    };

}
