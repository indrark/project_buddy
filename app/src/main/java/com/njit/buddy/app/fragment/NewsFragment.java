package com.njit.buddy.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.njit.buddy.app.R;
import com.njit.buddy.app.widget.PostView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author toyknight 8/15/2015.
 */
public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    public void updateNews(JSONArray list) {
        try {
            LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.news_layout);
            layout.removeAllViews();

            for (int i = 0; i < list.length(); i++) {
                JSONObject element = list.getJSONObject(i);
                PostView post = new PostView(getActivity(), element);
                layout.addView(post);
//                TextView tv = new TextView(getActivity());
//                tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//                tv.setText(element.getString("username") + " says: " + element.getString("content"));
//                layout.addView(tv);
            }
        } catch (JSONException ex) {
            Log.d("Error", ex.toString());
        }
    }

}
