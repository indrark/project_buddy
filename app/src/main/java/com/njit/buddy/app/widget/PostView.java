package com.njit.buddy.app.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.njit.buddy.app.CommentActivity;
import com.njit.buddy.app.HugActivity;
import com.njit.buddy.app.R;
import com.njit.buddy.app.entity.Post;
import com.njit.buddy.app.network.task.BellTask;
import com.njit.buddy.app.network.task.FlagTask;
import com.njit.buddy.app.network.task.HugTask;
import com.njit.buddy.app.util.DateParser;
import com.njit.buddy.app.util.Log;

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
        ImageView btn_flag = (ImageView) findViewById(R.id.btn_flag);
        btn_flag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tryFlag();
            }
        });
        ImageView btn_bell = (ImageView) findViewById(R.id.btn_bell);
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
        View btn_comment = findViewById(R.id.btn_comment);
        btn_comment.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(Color.LTGRAY);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_MOVE:
                        v.setBackgroundColor(Color.WHITE);
                        break;
                }
                return false;
            }
        });
        btn_comment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCommentActivity();
            }
        });
        updateView();
    }

    public Post getPostData() {
        return post_data;
    }

    private String getLocalUsername() {
        SharedPreferences preferences = getContext().getSharedPreferences("buddy", Context.MODE_PRIVATE);
        return preferences.getString(getResources().getString(R.string.key_username), "");
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
            String local_username = getLocalUsername();
            String post_username = getPostData().getUsername();
            String content = getPostData().getContent();
            String date = new DateParser().toString(getPostData().getTimestamp());
            int hug = getPostData().getHug();
            boolean flagged = getPostData().isFlagged();
            boolean belled = getPostData().isBelled();
            boolean hugged = getPostData().isHugged();

            ((TextView) findViewById(R.id.tv_username)).setText(post_username);
            ((TextView) findViewById(R.id.tv_date)).setText(date);
            ((TextView) findViewById(R.id.tv_content)).setText(content);
            //flag button
            ImageView btn_flag = (ImageView) findViewById(R.id.btn_flag);
            btn_flag.setImageDrawable(flagged ?
                    getResources().getDrawable(R.drawable.ic_flag_selected) :
                    getResources().getDrawable(R.drawable.ic_flag_unselected));
            //bell button
            ImageView btn_bell = (ImageView) findViewById(R.id.btn_bell);
            btn_bell.setImageDrawable(belled ?
                    getResources().getDrawable(R.drawable.ic_bell_selected) :
                    getResources().getDrawable(R.drawable.ic_bell_unselected));
            //hug button
            Button btn_hug = (Button) findViewById(R.id.btn_hug);
            if (local_username.equals(post_username)) {
                btn_hug.setText("Hug");
                btn_hug.setEnabled(false);
            } else {
                btn_hug.setText(hugged ? "Hugged" : "Hug");
            }
            //hugged button
            Button btn_hugged = (Button) findViewById(R.id.btn_hugged);
            if (local_username.equals(post_username)) {
                btn_hugged.setText(Integer.toString(hug));
            } else {
                btn_hugged.setText("-");
            }
            //for test
            ((TextView) findViewById(R.id.tv_comment_count)).setText("[0]");
        }
    }

    public void gotoHugActivity() {
        String local_username = getLocalUsername();
        String post_username = getPostData().getUsername();
        if (local_username.equals(post_username)) {
            Intent intent = new Intent(getContext(), HugActivity.class);
            intent.putExtra("pid", getPostData().getPID());
            getContext().startActivity(intent);
        }
    }

    public void gotoCommentActivity() {
        Intent intent = new Intent(getContext(), CommentActivity.class);
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
                Log.error("Flag", error_code);
            }
        };
        task.execute(getPostData().getPID());
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
                Log.error("Bell", error_code);
            }
        };
        task.execute(getPostData().getPID());
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
                Log.error("Hug", error_code);
            }
        };
        task.execute(getPostData().getPID());
    }

}
