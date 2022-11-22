package com.example.lib.model;

import java.io.Serializable;

public class TickModel implements Serializable {
    private String userId;
    private String storyId;

    public TickModel() {
    }

    public TickModel(String userId, String storyId) {
        this.userId = userId;
        this.storyId = storyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }
}
