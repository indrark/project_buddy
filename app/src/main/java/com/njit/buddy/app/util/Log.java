package com.njit.buddy.app.util;

import com.njit.buddy.app.network.ResponseValue;

/**
 * @author toyknight 11/30/2015.
 */
public class Log {

    public static void error(String tag, int error_code) {
        android.util.Log.d(tag, "Error code " + error_code + " [" + ResponseValue.toString(error_code) + "]");
    }

}
