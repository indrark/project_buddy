package com.njit.buddy.app.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.njit.buddy.app.HugActivity;
import com.njit.buddy.app.R;
import com.njit.buddy.app.entity.Post;
import com.njit.buddy.app.network.task.BellTask;
import com.njit.buddy.app.network.task.FlagTask;
import com.njit.buddy.app.network.task.HugTask;
import com.njit.buddy.app.util.DateParser;

/**
 * @author toyknight on 10/8/2015.
 */
public class PostView extends RelativeLayout {

    private Post post_data;

    public PostView(Context context) {
        this(context, null);
    }

    public PostView(Context context, Post post_data) {
        super(context);
        this.post_data = post_data;
        View.inflate(getContext(), R.layout.view_post, this);
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
        Button btn_hugged = (Button) findViewById(R.id.btn_hugged);
        btn_hugged.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHugActivity();
            }
        });
        updateView();
    }

    public Post getPostData() {
        return post_data;
    }

    public void setBellVisible(boolean visible) {
        View bell = findViewById(R.id.btn_bell);
        if (visible) {
            bell.setVisibility(VISIBLE);
        } else {
            bell.setVisibility(INVISIBLE);
        }
    }

    public void updateView() {
        if (getPostData() != null) {
            String username = getPostData().getUsername();
            String content = getPostData().getContent();
            String date = new DateParser().toString(getPostData().getTimestamp());
            int hug = getPostData().getHug();
            boolean flagged = getPostData().isFlagged();
            boolean belled = getPostData().isBelled();
            boolean hugged = getPostData().isHugged();

            ((TextView) findViewById(R.id.tv_username)).setText(username);
            ((TextView) findViewById(R.id.tv_date)).setText(date);
            ((TextView) findViewById(R.id.tv_content)).setText(content);
            //flag button
            TextView btn_flag = (TextView) findViewById(R.id.btn_flag);
            btn_flag.setText(flagged ? "Flagged" : "Flag");
            //bell button
            TextView btn_bell = (TextView) findViewById(R.id.btn_bell);
            btn_bell.setText(belled ? "Belled" : "Bell");
            //hug button
            Button btn_hug = (Button) findViewById(R.id.btn_hug);
            btn_hug.setText(hugged ? "Hugged" : "Hug");
            //hugged button
            Button btn_hugged = (Button) findViewById(R.id.btn_hugged);
            btn_hugged.setText(Integer.toString(hug));
        }
    }

    public void gotoHugActivity() {
        Intent intent = new Intent(getContext(), HugActivity.class);
        intent.putExtra("pid", getPostData().getID());
        getContext().startActivity(intent);
    }

    public void tryFlag() {
        FlagTask task = new FlagTask() {
            @Override
            public void onSuccess(Integer result) {
                getPostData().setFlagged(!getPostData().isFlagged());
                updateView();
            }

            @Override
            public void onFail(int error_code) {
                Log.d("Bell", "Error code " + error_code);
            }
        };
        task.execute(getPostData().getID());
    }

    public void tryBell() {
        BellTask task = new BellTask() {
            @Override
            public void onSuccess(Integer result) {
                getPostData().setBelled(!getPostData().isBelled());
                updateView();
            }

            @Override
            public void onFail(int error_code) {
                Log.d("Bell", "Error code " + error_code);
            }
        };
        task.execute(getPostData().getID());
    }

    public void tryHug() {
        HugTask task = new HugTask() {
            @Override
            public void onSuccess(Integer result) {
                if (getPostData().isHugged()) {
                    getPostData().setHug(getPostData().getHug() - 1);
                } else {
                    getPostData().setHug(getPostData().getHug() + 1);
                }
                getPostData().setHugged(!getPostData().isHugged());
                updateView();
            }

            @Override
            public void onFail(int error_code) {
                Log.d("Hug", "Error code " + error_code);
            }
        };
        task.execute(getPostData().getID());
    }

}
