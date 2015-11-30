package com.njit.buddy.app.network;

/**
 * @author toyknight 11/8/2015.
 */
public class ResponseValue {

    //server part
    public static final int BUDDY_OK = 1;
    public static final int BUDDY_OPERATION_FAILED = 0;
    public static final int BUDDY_CANNOT_HUG_YOURSELF = 106;

    //client part
    public static final int BUDDY_BAD_REQUEST = -1;

    private ResponseValue() {
    }

    public static String toString(int error_code) {
        switch (error_code) {
            case BUDDY_OK:
                return "Buddy OK";
            case BUDDY_OPERATION_FAILED:
                return "Operation failed";
            case BUDDY_CANNOT_HUG_YOURSELF:
                return "Cannot hug your self";
            case BUDDY_BAD_REQUEST:
                return "Bad request";
            default:
                return "Not defined";
        }
    }

}
