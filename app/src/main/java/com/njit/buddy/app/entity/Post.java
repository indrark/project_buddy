package com.njit.buddy.app.entity;

/**
 * @author toyknight 11/20/2015.
 */
public class Post {

    private final int id;
    private final String username;
    private final String content;
    private final long timestamp;

    private int hug;

    private boolean flagged;
    private boolean belled;
    private boolean hugged;

    public Post(int id, String username, String content, long timestamp) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setHug(int hug) {
        this.hug = hug;
    }

    public int getHug() {
        return hug;
    }

    public void setFlagged(boolean b) {
        this.flagged = b;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setBelled(boolean b) {
        this.belled = b;
    }

    public boolean isBelled() {
        return belled;
    }

    public void setHugged(boolean b) {
        this.hugged = b;
    }

    public boolean isHugged() {
        return hugged;
    }

}
