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
import com.njit.buddy.app.widget.PostView;

import java.util.ArrayList;

/**
 * @author toyknight 8/15/2015.
 */
public class AttentionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attention, container, false);
    }

    public void tryUpdateAttentionList() {
        PostListTask task = new PostListTask() {
            @Override
            public void onSuccess(ArrayList<Post> post_list) {
                updateAttentionList(post_list);
            }

            @Override
            public void onFail(int error_code) {
                Log.d("Attention", "Error code " + error_code);
            }
        };
        task.execute(0, 10, 1);
    }

    private void updateAttentionList(ArrayList<Post> post_list) {
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.attention_layout);
        layout.removeAllViews();
        for (Post post : post_list) {
            PostView post_view = new PostView(getActivity(), post);
            post_view.setBellVisible(false);
            layout.addView(post_view);
        }
    }

}
