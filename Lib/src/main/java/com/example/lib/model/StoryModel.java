package com.example.lib.model;

import android.icu.lang.UProperty;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.util.List;

public class StoryModel implements Serializable {
    private String image;

    private String updateDate;

    private String name;

    private String description;

    private String id;

    private String authorId;

    private String authorName;

    private String userId;

    private String views;

    private String status;

    private String createDate;

    private List<String> categoryName;

    public List<String> getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(List<String> categoryName) {
        this.categoryName = categoryName;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getUpdateDate ()
    {
        return updateDate;
    }

    public void setUpdateDate (String updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAuthorId ()
    {
        return authorId;
    }

    public void setAuthorId (String authorId)
    {
        this.authorId = authorId;
    }

    public String getAuthorName ()
    {
        return authorName;
    }

    public void setAuthorName (String authorName)
    {
        this.authorName = authorName;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getViews ()
    {
        return views;
    }

    public void setViews (String views)
    {
        this.views = views;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getCreateDate ()
    {
        return createDate;
    }

    public void setCreateDate (String createDate)
    {
        this.createDate = createDate;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", updateDate = "+updateDate+", name = "+name+", description = "+description+", id = "+id+", authorId = "+authorId+", userId = "+userId+", views = "+views+", status = "+status+", createDate = "+createDate+"]";
    }
}
