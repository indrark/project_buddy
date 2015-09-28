package com.njit.buddy.app;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;

/**
 * @author toyknight 8/16/2015.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private AlertDialog editor_birthday;
    private DatePicker birthday_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initComponents();
        createDialogs();
    }

    @SuppressWarnings("ResourceType")
    private void initComponents() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.abs_profile);

            getSupportActionBar().getCustomView().findViewById(R.id.btn_profile_back).setOnClickListener(btn_back_click_listener);
            getSupportActionBar().getCustomView().findViewById(R.id.btn_profile_edit).setOnClickListener(btn_edit_click_listener);
            getSupportActionBar().getCustomView().findViewById(R.id.btn_profile_edit).setVisibility(View.INVISIBLE);
        }

        View btn_birthday = findViewById(R.id.btn_birthday);
        View btn_sex = findViewById(R.id.btn_sex);
        View btn_sexuality = findViewById(R.id.btn_sexuality);
        View btn_race = findViewById(R.id.btn_race);

        btn_birthday.setOnTouchListener(btn_touch_listener);
        btn_birthday.setOnClickListener(this);
        btn_sex.setOnTouchListener(btn_touch_listener);
        btn_sex.setOnClickListener(this);
        btn_sexuality.setOnTouchListener(btn_touch_listener);
        btn_sexuality.setOnClickListener(this);
        btn_race.setOnTouchListener(btn_touch_listener);
        btn_race.setOnClickListener(this);
    }

    private void createDialogs() {
        AlertDialog.Builder birthday_builder = new AlertDialog.Builder(this);
        View birthday_dialog_content = getLayoutInflater().inflate(R.layout.editor_birthday, null);
        birthday_picker = (DatePicker) birthday_dialog_content.findViewById(R.id.birthday_picker);
        birthday_builder.setView(birthday_dialog_content);
        birthday_builder.setPositiveButton(getResources().getString(R.string.label_set), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        birthday_builder.setNegativeButton(getResources().getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        editor_birthday = birthday_builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_birthday:
                editor_birthday.show();
                break;
            case R.id.btn_sex:
                break;
            case R.id.btn_sexuality:
                break;
            case R.id.btn_race:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

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
                case MotionEvent.ACTION_MOVE:
                    v.setBackgroundResource(R.drawable.border_unchecked);
                    break;
            }
            return false;
        }

    };

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
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }

    };

}
