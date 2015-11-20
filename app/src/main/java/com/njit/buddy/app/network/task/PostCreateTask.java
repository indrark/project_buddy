package com.njit.buddy.app.network.task;

import android.os.AsyncTask;
import android.util.Log;
import com.njit.buddy.app.network.Connector;
import com.njit.buddy.app.network.ResponseValue;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author toyknight 10/3/2015.
 */
public abstract class PostCreateTask extends AsyncTask<String, Void, Integer> implements ResponseHandler<Integer> {

    @Override
    protected Integer doInBackground(String... params) {
        String category = params[0];
        String content = params[1];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("category_id", category);
            request_body.put("content", content);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/post/create", request_body.toString());
            JSONObject response = new JSONObject(result);
            return response.getInt("responsevalue");
        } catch (JSONException ex) {
            Log.d("Network", ex.toString());
            return ResponseValue.BUDDY_BAD_REQUEST;
        } catch (IOException ex) {
            Log.d("Network", ex.toString());
            return ResponseValue.BUDDY_BAD_REQUEST;
        }
    }

    @Override
    protected final void onPostExecute(Integer response_value) {
        if (response_value == ResponseValue.BUDDY_OK) {
            onSuccess(response_value);
        } else {
            onFail(response_value);
        }
    }

}
