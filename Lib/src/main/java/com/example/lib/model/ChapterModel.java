package com.example.lib.model;

import java.io.Serializable;

public class ChapterModel implements Serializable {
    private String storyId;

    private int numberChapter;

    private String name;

    private String content;

    private String storyName;

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getStoryId ()
    {
        return storyId;
    }

    public void setStoryId (String storyId)
    {
        this.storyId = storyId;
    }

    public int getNumberChapter ()
    {
        return numberChapter;
    }

    public void setNumberChapter (int numberChapter)
    {
        this.numberChapter = numberChapter;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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
        return "ClassPojo [storyId = "+storyId+", numberChapter = "+numberChapter+", name = "+name+", content = "+content+"]";
    }
}
