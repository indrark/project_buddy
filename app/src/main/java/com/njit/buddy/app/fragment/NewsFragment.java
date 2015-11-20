package com.njit.buddy.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.njit.buddy.app.R;
import com.njit.buddy.app.entity.Post;
import com.njit.buddy.app.network.task.PostListTask;
import com.njit.buddy.app.network.task.PostCreateTask;
import com.njit.buddy.app.widget.PostView;

import java.util.ArrayList;

/**
 * @author toyknight 8/15/2015.
 */
public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    public void tryPost(String content, int selected_category) {
        PostCreateTask task = new PostCreateTask();
        task.execute(Integer.toString(selected_category), content);
        tryUpdateNewsList();
    }

    public void tryUpdateNewsList() {
        PostListTask task = new PostListTask() {
            @Override
            public void onSuccess(ArrayList<Post> post_list) {
                updateNewsList(post_list);
            }

            @Override
            public void onFail(int error_code) {
                Log.d("News", "Error code " + error_code);
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
