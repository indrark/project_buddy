package com.njit.buddy.app.network.task;

import android.os.AsyncTask;
import android.util.Log;
import com.njit.buddy.app.entity.Post;
import com.njit.buddy.app.network.Connector;
import com.njit.buddy.app.network.ResponseValue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author by toyknight 10/3/2015.
 */
public abstract class PostListTask
        extends AsyncTask<Integer, Void, JSONArray> implements ResponseHandler<ArrayList<Post>> {

    @Override
    protected JSONArray doInBackground(Integer... params) {
        Integer start = params[0];
        Integer count = params[1];
        Integer attention = params[2];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("start", start);
            request_body.put("count", count);
            request_body.put("attention", attention);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/post/list", request_body.toString());
            return new JSONArray(result);
        } catch (JSONException ex) {
            Log.d("JSON", ex.toString());
            return null;
        } catch (IOException ex) {
            Log.d("Network", ex.toString());
            return null;
        }
    }

    @Override
    protected final void onPostExecute(JSONArray result) {
        if (result == null) {
            onFail(ResponseValue.BUDDY_BAD_REQUEST);
        } else {
            ArrayList<Post> post_list = new ArrayList<Post>();
            for (int i = 0; i < result.length(); i++) {
                try {
                    JSONObject element = result.getJSONObject(i);
                    Post post = new Post(
                            element.getInt("pid"),
                            element.getString("username"),
                            element.getString("content"),
                            element.getLong("date_time"));
                    post.setHug(element.getInt("hug"));
                    post.setFlagged(element.getInt("flag") != 0);
                    post.setBelled(element.getInt("attention") != 0);
                    post.setHugged(element.getInt("huged") != 0);
                    post_list.add(post);
                } catch (JSONException ex) {
                    Log.d("Error", ex.toString());
                }
            }
            onSuccess(post_list);
        }
    }

}
