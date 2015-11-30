package com.njit.buddy.app.entity;

/**
 * @author toyknight 11/23/2015.
 */
public class Hug {

    private final int uid;
    private final String username;

    private boolean hugged_back = false;

    public Hug(int uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public int getUID() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setHuggedBack(boolean hugged_back) {
        this.hugged_back = hugged_back;
    }

    public boolean isHuggedBack() {
        return hugged_back;
    }

}
