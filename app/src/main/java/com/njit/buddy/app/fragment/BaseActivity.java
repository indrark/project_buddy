package com.njit.buddy.app.fragment;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.njit.buddy.app.R;

/**
 * Created by toyknight on 9/13/2015.
 */
public class BaseActivity extends AppCompatActivity {

    public final String getPreference(String key) {
        return getApplicationContext().getSharedPreferences(getResources().getString(R.string.key_preference), Context.MODE_PRIVATE).getString(key, null);
    }

    public final void setPreference(String key, String value) {

    }

}
