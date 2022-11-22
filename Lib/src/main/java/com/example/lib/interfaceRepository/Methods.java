package com.example.lib.interfaceRepository;

import com.example.lib.model.CategoryModel;
import com.example.lib.model.ChapterModel;
import com.example.lib.model.StoryModel;
import com.example.lib.model.TickModel;
import com.example.lib.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Methods {
    // list category
    @GET("api/Category")
    Call<List<CategoryModel>> getCategory();

    // list story
    @GET("api/Story")
    Call<List<StoryModel>> getStory();
    @GET("api/Story/search")
    Call<List<StoryModel>> seachStory(@Query("name") String storyName);
    @GET("api/Story/{storyId}")
    Call<StoryModel> getStory1(@Path("storyId") String storyId);

    //list chapter
    @GET("api/chapter/{storyId}")
    Call<List<ChapterModel>> getChapter(@Path("storyId") String storyId);

    /* ---------------------------------------------------------------------------------*/
    // find and check user
    @GET("api/ApplicationUser")
    Call<List<UserModel>> getUsers();
    @POST("api/ApplicationUser/CheckUser")
    Call<UserModel> postUser(@Body UserModel model);

    /* ---------------------------------------------------------------------------------*/
    // tick
    @POST("api/Story/Tick")
    Call<TickModel> postTick(@Body TickModel model);

    @GET("api/Story/Tick")
    Call<List<StoryModel>> getTick(@Query("userId") String userId);

    @GET("api/Story/ExistTick/{userId}/{storyId}")
    Call<TickModel> getExistTick(@Path("userId") String userId, @Path("storyId") String storyId);

    @HTTP(method = "DELETE", path = "api/Story/Tick", hasBody = true)
    Call<TickModel> deleteTick(@Body TickModel model);
}