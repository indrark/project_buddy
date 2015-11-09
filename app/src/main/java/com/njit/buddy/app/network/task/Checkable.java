package com.njit.buddy.app.network.task;

/**
 * @author toyknight 11/9/2015.
 */
public interface Checkable<T> {

    void onSuccess(T result);

    void onFail(int error_code);

}
