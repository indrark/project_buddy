package com.njit.buddy.app.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.njit.buddy.app.R;
import com.njit.buddy.app.entity.Hug;

/**
 * @author toyknight 11/23/2015.
 */
public class HugView extends LinearLayout {

    private Hug hug_data;

    public HugView(Context context) {
        this(context, null);
    }

    public HugView(Context context, Hug hug_data) {
        super(context);
        this.hug_data = hug_data;
        View.inflate(getContext(), R.layout.view_hug, this);
        updateView();
    }

    public Hug getHugData() {
        return hug_data;
    }

    public void updateView() {
        TextView tv_username = (TextView) findViewById(R.id.tv_username);
        tv_username.setText(getHugData().getUsername());
    }

}
