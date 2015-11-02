package com.njit.buddy.app.network;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author toyknight 11/2/2015.
 */
public abstract class RegisterTask extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        String email = params[0];
        String username = params[1];
        String password = params[2];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("email", email);
            request_body.put("username", username);
            request_body.put("password", password);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/login", request_body.toString());
            JSONObject response = new JSONObject(result);
            return response.getInt("responsecode") == 1;
        } catch (JSONException ex) {
            Log.d("Login", ex.toString());
            return false;
        } catch (IOException ex) {
            Log.d("Login", ex.toString());
            return false;
        }
    }

    @Override
    abstract protected void onPostExecute(final Boolean approved);

}
