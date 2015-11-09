package com.njit.buddy.app.network.task;

import android.os.AsyncTask;
import android.util.Log;
import com.njit.buddy.app.network.Connector;
import com.njit.buddy.app.network.ResponseValue;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author toyknight 10/22/2015.
 */
public abstract class LoginTask extends AsyncTask<String, Void, JSONObject> implements Checkable<String> {

    @Override
    protected JSONObject doInBackground(String... params) {
        String email = params[0];
        String password = params[1];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("email", email);
            request_body.put("password", password);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/login", request_body.toString());
            return new JSONObject(result);
        } catch (JSONException ex) {
            Log.d("Login", ex.toString());
            return null;
        } catch (IOException ex) {
            Log.d("Login", ex.toString());
            return null;
        }
    }

    @Override
    protected final void onPostExecute(JSONObject response) {
        if (response == null) {
            onFail(ResponseValue.BUDDY_BAD_REQUEST);
        } else {
            try {
                int response_value = response.getInt("responsevalue");
                if (response_value == ResponseValue.BUDDY_OK) {
                    String token = response.getString("token");
                    onSuccess(token);
                } else {
                    Log.d("Login", "Error code: " + response_value);
                    onFail(response_value);
                }
            } catch (JSONException ex) {
                Log.d("Login", ex.toString());
                onFail(ResponseValue.BUDDY_BAD_REQUEST);
            }
        }
    }

}
