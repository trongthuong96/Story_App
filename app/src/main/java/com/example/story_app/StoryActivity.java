package com.example.story_app;

import static com.example.lib.RetrofitClient.getRetrofit;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.ChapterModel;
import com.example.lib.model.StoryModel;
import com.example.lib.model.TickModel;
import com.example.story_app.Session.SessionUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryActivity extends AppCompatActivity {

    TextView txtNameStoryDetail;
    ImageView imgStoryDetail;
    TextView txtAuthorDetail;
    TextView txtCateStoryDetail;
    TextView txtStatusDetail;
    TextView txtSummaryDetail;
    RelativeLayout relativeBackground;
    Button btnTick;
    String id;
    StoryModel model;

    //tick
    TickModel tickModel;

    //key save chapter
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        txtNameStoryDetail = findViewById(R.id.txtNameStoryDetail);
        imgStoryDetail = findViewById(R.id.imgStoryDetail);
        txtAuthorDetail = findViewById(R.id.txtAuthorDetail);
        txtCateStoryDetail = findViewById(R.id.txtCateStoryDetail);
        txtStatusDetail = findViewById(R.id.txtStatusDetail);
        txtSummaryDetail = findViewById(R.id.txtSummaryDetail);
        relativeBackground = findViewById(R.id.relativeBackground);
        btnTick = findViewById(R.id.btnTick);

        //story
        Intent intent = getIntent();
        model = (StoryModel) intent.getSerializableExtra("model");

        //story
        id = model.getId();
        txtNameStoryDetail.setText(model.getName());
        txtAuthorDetail.setText(model.getAuthorName());
        txtCateStoryDetail.setText(model.getCategoryName().get(0));
        txtStatusDetail.setText(model.getStatus());
        txtSummaryDetail.setText(Html.fromHtml(model.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        Glide.with(this).load(model.getImage()).into(imgStoryDetail);

        Glide.with(this)
                .load(model.getImage())
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        relativeBackground.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        //tick
        tickModel = new TickModel(new SessionUser(getApplicationContext()).GetIdUser(), model.getId());
        GetExistTick(tickModel.getUserId(), tickModel.getStoryId());
    }

    public void goToBackHome(View view) {
        finish();
    }

    // go to list chapter
    public void goToChapter(View view) {
        //model into detail story
        Intent intent = new Intent(this, ChapterActivity.class);
        intent.putExtra("modelStory", model);
        startActivity(intent);
    }

    // check exist tick
    private void GetExistTick(String userId, String storyId){

        Methods methods = getRetrofit().create(Methods.class);
        Call<TickModel> call = methods.getExistTick(userId, storyId);
        call.enqueue(new Callback<TickModel>() {
            @Override
            public void onResponse(Call<TickModel> call, Response<TickModel> response) {
                if(response.code() == 200){
                    btnTick.setText("Đã đánh dấu");
                }
            }

            @Override
            public void onFailure(Call<TickModel> call, Throwable t) {
                t.toString();
            }
        });
    }


    //read chapter from button readStory
    public void goToChapterDetail(View view) {
        GetChapter();
    }

    // get list chapter
    private void GetChapter(){

        Methods methods = getRetrofit().create(Methods.class);
        Call<List<ChapterModel>> call = methods.getChapter(model.getId());
        call.enqueue(new Callback<List<ChapterModel>>() {
            @Override
            public void onResponse(Call<List<ChapterModel>> call, Response<List<ChapterModel>> response) {

                //key save chapter
                sharedPref = getSharedPreferences("ChapterSave", Activity.MODE_PRIVATE);

                //chapter of story read
                int chapterNumber = sharedPref.getInt(model.getId().toString(),-1);

                // find chater read
                if(chapterNumber == -1){
                    try {
                        Intent intent = new Intent(StoryActivity.this, ChapterDetailActivity.class);
                        intent.putExtra("model",  response.body().get(0));
                        //save key chapter off
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(model.getId().toString(), 1).commit();
                        startActivity(intent);
                    }catch (Exception e){e.printStackTrace();}

                }else {
                    ChapterModel chapterModel = null;

                    for (ChapterModel item: response.body()){
                        if(chapterNumber == item.getNumberChapter()){
                            chapterModel = item;
                            break;
                        }
                    }
                    Intent intent = new Intent(StoryActivity.this, ChapterDetailActivity.class);
                    intent.putExtra("model",  chapterModel);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<ChapterModel>> call, Throwable t) {

            }
        });

    }

    //button tick
    public void setTick(View view) {
        if(new SessionUser(getApplicationContext()).GetIdUser() != null){
            if(btnTick.getText().equals("Đánh dấu")){
                Methods methods = getRetrofit().create(Methods.class);
                Call<TickModel> call = methods.postTick(tickModel);
                call.enqueue(new Callback<TickModel>() {
                    @Override
                    public void onResponse(Call<TickModel> call, Response<TickModel> response) {
                        if(response.code() == 200){
                            btnTick.setText("Đã đánh dấu");
                        }
                    }

                    @Override
                    public void onFailure(Call<TickModel> call, Throwable t) {

                    }
                });
            }else {
                Methods methods = getRetrofit().create(Methods.class);
                Call<TickModel> call = methods.deleteTick(tickModel);
                call.enqueue(new Callback<TickModel>() {
                    @Override
                    public void onResponse(Call<TickModel> call, Response<TickModel> response) {
                        if(response.code() == 200){
                            btnTick.setText("Đánh dấu");
                        }
                    }

                    @Override
                    public void onFailure(Call<TickModel> call, Throwable t) {

                    }
                });
            }

        }else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Thông báo!");
            alert.setIcon(R.drawable.ic_baseline_info_24);
            alert.setMessage("Đăng nhập tài khoản!");

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();
        }
    }

    // Comment
    public void goToComment(View view) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("modelStory", model);
        startActivity(intent);
    }
}