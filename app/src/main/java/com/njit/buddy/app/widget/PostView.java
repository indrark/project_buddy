package com.njit.buddy.app.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.njit.buddy.app.R;
import com.njit.buddy.app.network.task.BellTask;
import com.njit.buddy.app.network.task.FlagTask;
import com.njit.buddy.app.network.task.HugTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author toyknight on 10/8/2015.
 */
public class PostView extends RelativeLayout {

    private JSONObject post_data;

    public PostView(Context context) {
        this(context, null);
    }

    public PostView(Context context, JSONObject post_data) {
        super(context);
        this.post_data = post_data;
        View.inflate(getContext(), R.layout.view_post, this);

        if (getPostData() != null) {
            try {
                String username = getPostData().getString("username");
                Long date_time = getPostData().getLong("date_time");
                String date_string = SimpleDateFormat.getDateInstance().format(new Date(date_time));
                String content = getPostData().getString("content");
                ((TextView) findViewById(R.id.tv_username)).setText(username);
                ((TextView) findViewById(R.id.tv_date)).setText(date_string);
                ((TextView) findViewById(R.id.tv_content)).setText(content);
                TextView btn_flag = (TextView) findViewById(R.id.btn_flag);
                btn_flag.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tryFlag();
                    }
                });
                TextView btn_bell = (TextView) findViewById(R.id.btn_bell);
                btn_bell.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tryBell();
                    }
                });
                Button btn_hug = (Button) findViewById(R.id.btn_hug);
                btn_hug.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tryHug();
                    }
                });
                updateButtons();
            } catch (JSONException ex) {
                Log.d("JSON", ex.toString());
            }
        }
    }

    public JSONObject getPostData() {
        return post_data;
    }

    public int getPostID() {
        try {
            return post_data.getInt("pid");
        } catch (JSONException ex) {
            return -1;
        }
    }

    public void setBellVisible(boolean visible) {
        View bell = findViewById(R.id.btn_bell);
        if (visible) {
            bell.setVisibility(VISIBLE);
        } else {
            bell.setVisibility(INVISIBLE);
        }
    }

    public void updateButtons() throws JSONException {
        //flag button
        TextView btn_flag = (TextView) findViewById(R.id.btn_flag);
        btn_flag.setText(getPostData().getInt("flag") == 0 ? "Flag" : "Flagged");
        //bell button
        TextView btn_bell = (TextView) findViewById(R.id.btn_bell);
        btn_bell.setText(getPostData().getInt("attention") == 0 ? "Bell" : "Belled");
        //hug button
        Button btn_hug = (Button) findViewById(R.id.btn_hug);
        btn_hug.setText(getPostData().getInt("huged") == 0 ? "Hug" : "Hugged");
        //hugged button
        Button btn_hugged = (Button) findViewById(R.id.btn_hugged);
        int hugged = getPostData().getInt("hug");
        btn_hugged.setText(Integer.toString(hugged));

    }

    public void tryFlag() {
        FlagTask task = new FlagTask() {
            @Override
            public void onSuccess(Integer result) {
                TextView btn_bell = (TextView) findViewById(R.id.btn_flag);
                String text = btn_bell.getText().toString().equals("Flag") ? "Flagged" : "Flag";
                btn_bell.setText(text);
            }

            @Override
            public void onFail(int error_code) {
                Log.d("Bell", "Error code " + error_code);
            }
        };
        task.execute(getPostID());
    }

    public void tryBell() {
        BellTask task = new BellTask() {
            @Override
            public void onSuccess(Integer result) {
                TextView btn_bell = (TextView) findViewById(R.id.btn_bell);
                String text = btn_bell.getText().toString().equals("Bell") ? "Belled" : "Bell";
                btn_bell.setText(text);
            }

            @Override
            public void onFail(int error_code) {
                Log.d("Bell", "Error code " + error_code);
            }
        };
        task.execute(getPostID());
    }

    public void tryHug() {
        HugTask task = new HugTask() {
            @Override
            public void onSuccess(Integer result) {
                Button btn_hug = (Button) findViewById(R.id.btn_hug);
                String text = btn_hug.getText().toString().equals("Hug") ? "Hugged" : "Hug";
                btn_hug.setText(text);
            }

            @Override
            public void onFail(int error_code) {
                Log.d("HUG", "Error code " + error_code);
            }
        };
        task.execute(getPostID());
    }

}
