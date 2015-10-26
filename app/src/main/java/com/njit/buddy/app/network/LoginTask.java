package com.njit.buddy.app.network;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author toyknight 10/22/2015.
 */
public class LoginTask extends AsyncTask<String, Void, Integer> {

    @Override
    protected Integer doInBackground(String... params) {
        String email = params[0];
        String password = params[1];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("email", email);
            request_body.put("password", password);

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
