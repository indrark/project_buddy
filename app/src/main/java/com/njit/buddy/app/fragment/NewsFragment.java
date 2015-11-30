package com.njit.buddy.app.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.njit.buddy.app.R;
import com.njit.buddy.app.entity.Post;
import com.njit.buddy.app.network.task.PostListTask;
import com.njit.buddy.app.network.task.PostCreateTask;
import com.njit.buddy.app.util.Log;
import com.njit.buddy.app.widget.PostView;

import java.util.ArrayList;

/**
 * @author toyknight 8/15/2015.
 */
public class NewsFragment extends Fragment {

    private AlertDialog category_list;
    private AlertDialog post_dialog;
    private EditText content_input;

    private int selected_category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createDialogs();
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    private void createDialogs() {
        //create category list
        AlertDialog.Builder category_builder = new AlertDialog.Builder(getActivity());
        category_builder.setTitle(R.string.msg_category)
                .setItems(R.array.category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showPostDialog(which);
                    }
                });
        category_list = category_builder.create();
        //create post dialog
        AlertDialog.Builder post_builder = new AlertDialog.Builder(getActivity());
        post_builder.setTitle(R.string.msg_say_something);
        content_input = new EditText(getActivity());
        post_builder.setView(content_input);
        post_builder.setPositiveButton(getResources().getString(R.string.label_post), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String content = content_input.getText().toString();
                tryPost(content, selected_category);
                post_dialog.dismiss();
            }
        });
        post_builder.setNegativeButton(getResources().getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        post_dialog = post_builder.create();
    }

    private void showPostDialog(int selected_category) {
        this.selected_category = selected_category;
        content_input.setText("");
        category_list.dismiss();
        post_dialog.show();
    }

    public void startPostingProgress() {
        category_list.show();
    }

    public void tryPost(String content, int selected_category) {
        PostCreateTask task = new PostCreateTask() {
            @Override
            public void onSuccess(Integer result) {
                tryUpdateNewsList();
            }

            @Override
            public void onFail(int error_code) {
                Log.error("Post", error_code);
            }
        };
        task.execute(Integer.toString(selected_category), content);
    }

    public void tryUpdateNewsList() {
        PostListTask task = new PostListTask() {
            @Override
            public void onSuccess(ArrayList<Post> post_list) {
                updateNewsList(post_list);
            }

            @Override
            public void onFail(int error_code) {
                Log.error("News", error_code);
            }
        };
        task.execute(0, 10, 0);
    }

    public void updateNewsList(ArrayList<Post> post_list) {
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.news_layout);
        layout.removeAllViews();
        for (Post post : post_list) {
            PostView post_view = new PostView(getActivity(), post);
            layout.addView(post_view);
        }
    }

}
