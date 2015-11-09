package com.njit.buddy.app.network.task;

import android.os.AsyncTask;
import com.njit.buddy.app.network.Connector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author by toyknight 10/3/2015.
 */
public abstract class NewsListTask extends AsyncTask<Integer, Void, JSONArray> implements Checkable<JSONArray> {

    @Override
    protected JSONArray doInBackground(Integer... params) {
        Integer start = params[0];
        Integer count = params[1];

        try {
            JSONObject request_body = new JSONObject();
            request_body.put("start", start);
            request_body.put("count", count);

            String result = Connector.executePost(Connector.SERVER_ADDRESS + "/post/list", request_body.toString());
            return new JSONArray(result);
        } catch (JSONException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    protected final void onPostExecute(JSONArray result) {
        onSuccess(result);
    }

}
