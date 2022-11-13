package com.example.lib.interfaceRepository;

import com.example.lib.model.CategoryModel;
import com.example.lib.model.StoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {
    // list category
    @GET("api/Category")
    Call<List<CategoryModel>> getCategory();

    // list story
    @GET("api/Story")
    Call<List<StoryModel>> getStory();
}
