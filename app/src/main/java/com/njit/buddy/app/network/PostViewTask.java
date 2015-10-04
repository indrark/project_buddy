package com.njit.buddy.app.network;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author by toyknight 10/3/2015.
 */
public class PostViewTask extends AsyncTask<String, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... params) {
        String filter = params[0];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("filter", filter);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/post/create", request_body.toString());
            return new JSONObject(result);
        } catch (JSONException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

}
