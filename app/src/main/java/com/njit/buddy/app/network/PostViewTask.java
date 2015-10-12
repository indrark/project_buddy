package com.njit.buddy.app.network;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author by toyknight 10/3/2015.
 */
public class PostViewTask extends AsyncTask<String, Void, JSONArray> {

    @Override
    protected JSONArray doInBackground(String... params) {
        String start = params[0];
        String count = params[1];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("start", start);
            request_body.put("count", count);
            request_body.put("uid", "31");

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/post/list", request_body.toString());
            return new JSONArray(result);
        } catch (JSONException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

}
