package com.njit.buddy.app.entity;

/**
 * @author toyknight 11/23/2015.
 */
public class Profile {

    private final int uid;

    private String username = "";

    public Profile(int uid) {
        this.uid = uid;
    }

    public int getUID() {
        return uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
