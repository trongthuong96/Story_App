package com.example.lib.model;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String name;

    private String description;

    private String id;

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

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", description = "+description+", id = "+id+"]";
    }
}
