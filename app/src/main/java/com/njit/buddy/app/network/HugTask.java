package com.njit.buddy.app.network;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by toyknight on 10/8/2015.
 */
public class HugTask extends AsyncTask<Integer, Void, Integer> {

    @Override
    protected Integer doInBackground(Integer... params) {
        Integer pid = params[0];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("pid", pid);
            request_body.put("uid", "31");

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/hug", request_body.toString());
            JSONObject response = new JSONObject(result);
            return response.getInt("responsevalue");
        } catch (JSONException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

}
