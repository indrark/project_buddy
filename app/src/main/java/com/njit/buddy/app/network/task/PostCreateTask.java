package com.njit.buddy.app.network.task;

import android.os.AsyncTask;
import com.njit.buddy.app.network.Connector;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author toyknight 10/3/2015.
 */
public class PostCreateTask extends AsyncTask<String, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... params) {
        String category = params[0];
        String content = params[1];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("category_id", category);
            request_body.put("content", content);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/post/create", request_body.toString());
            return new JSONObject(result);
        } catch (JSONException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }


}
