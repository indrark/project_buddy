package com.njit.buddy.app.network.task;

import android.os.AsyncTask;
import android.util.Log;
import com.njit.buddy.app.entity.Profile;
import com.njit.buddy.app.network.Connector;
import com.njit.buddy.app.network.ResponseValue;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author toyknight 11/23/2015.
 */
public abstract class ProfileViewTask extends AsyncTask<Integer, Void, JSONObject> implements ResponseHandler<Profile> {

    @Override
    protected JSONObject doInBackground(Integer... params) {
        Integer uid = params[0];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("uid", uid);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/profile/view", request_body.toString());
            System.out.println(result);
            return new JSONObject(result);
        } catch (JSONException ex) {
            Log.d("JSON", ex.toString());
            return null;
        } catch (IOException ex) {
            Log.d("Network", ex.toString());
            return null;
        }
    }

    @Override
    protected final void onPostExecute(JSONObject result) {
        if (result == null) {
            onFail(ResponseValue.BUDDY_BAD_REQUEST);
        } else {
            try {
                Profile profile = new Profile(result.getInt("uid"));
//                profile.setUsername(result.getString("username"));
                onSuccess(profile);
            } catch (JSONException ex) {
                Log.d("Error", ex.toString());
                onFail(ResponseValue.BUDDY_BAD_REQUEST);
            }
        }
    }

}
