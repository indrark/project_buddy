package com.njit.buddy.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.njit.buddy.app.R;
import com.njit.buddy.app.network.task.AttentionListTask;
import com.njit.buddy.app.widget.PostView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author toyknight 8/15/2015.
 */
public class AttentionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attention, container, false);
    }

    public void tryUpdateAttentionList() {
        AttentionListTask task = new AttentionListTask() {
            @Override
            public void onSuccess(JSONArray result) {
                updateAttentionList(result);
            }

            @Override
            public void onFail(int error_code) {
            }
        };
        task.execute(0, 10);
    }

    private void updateAttentionList(JSONArray list) {
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.attention_layout);
        layout.removeAllViews();
        for (int i = 0; i < list.length(); i++) {
            try {
                JSONObject element = list.getJSONObject(i);
                PostView post = new PostView(getActivity(), element);
                post.setBellVisible(false);
                layout.addView(post);
            } catch (JSONException ex) {
                Log.d("Error", ex.toString());
            }
        }
    }

}
