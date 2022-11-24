package com.example.lib.model;


import java.io.Serializable;

public class CommentModel implements Serializable
{
    private String storyId;

    private String id;

    private String userName;

    private String userId;

    private String content;

    private String createDate;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStoryId ()
    {
        return storyId;
    }

    public void setStoryId (String storyId)
    {
        this.storyId = storyId;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUserName ()
{
    return userName;
}

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [storyId = "+storyId+", id = "+id+", userName = "+userName+", userId = "+userId+", content = "+content+"]";
    }
}


