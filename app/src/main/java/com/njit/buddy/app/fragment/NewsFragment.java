package com.njit.buddy.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import com.njit.buddy.app.R;
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

    public void updateNews(JSONObject data) {
        try {
            LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.news_layout);
            layout.removeAllViews();

            JSONArray list = data.getJSONArray("content");
            for (int i = 0; i < list.length(); i++) {
                JSONObject element = list.getJSONObject(i);
                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                tv.setText(element.getString("username") + " says: " + element.getString("content"));
                layout.addView(tv);
            }
        } catch (JSONException ex) {
            Log.d("Error", ex.toString());
        }
    }

}
