package com.njit.buddy.app.network;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author toyknight 10/22/2015.
 */
public abstract class LoginTask extends AsyncTask<String, Void, Integer> {

    private String token = null;

    @Override
    protected Integer doInBackground(String... params) {
        String email = params[0];
        String password = params[1];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("email", email);
            request_body.put("password", password);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/login", request_body.toString());
            JSONObject response = new JSONObject(result);
            int response_value = response.getInt("responsevalue");
            if (response_value == ResponseValue.BUDDY_OK) {
                token = response.getString("token");
            }
            return response_value;
        } catch (JSONException ex) {
            Log.d("Login", ex.toString());
            return null;
        } catch (IOException ex) {
            Log.d("Login", ex.toString());
            return null;
        }
    }

    @Override
    protected final void onPostExecute(Integer response_value) {
        if (response_value == null) {
            response_value = ResponseValue.BUDDY_BAD_REQUEST;
        }
        if (response_value == ResponseValue.BUDDY_OK) {
            onLoginSuccess(token);
        } else {
            Log.d("Login", "Error code: " + response_value);
            onLoginFail(response_value);
        }
    }

    abstract public void onLoginSuccess(String token);

    abstract public void onLoginFail(int error_code);

}
