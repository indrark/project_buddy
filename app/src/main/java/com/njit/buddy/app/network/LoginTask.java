package com.njit.buddy.app.network;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author toyknight 10/22/2015.
 */
public class LoginTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String email = params[0];
        String password = params[1];

        boolean login_success;
        try {
            JSONObject request_body = new JSONObject();
            request_body.put("email", email);
            request_body.put("password", password);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/login", request_body.toString());
            JSONObject response = new JSONObject(result);
            if (response.getInt("responsevalue") == 1) {
                return response.getString("session");
            } else {
                return null;
            }
        } catch (JSONException ex) {
            Log.d("Login", ex.toString());
            return null;
        } catch (IOException ex) {
            Log.d("Login", ex.toString());
            return null;
        }
    }

}
