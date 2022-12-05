package com.example.story_app;

import static com.example.lib.RetrofitClient.getRetrofit;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.ChapterModel;
import com.example.story_app.Adapter.ChapterRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterDetailActivity extends AppCompatActivity {

    //chapter
    ChapterModel model;
    ChapterRecycleViewAdapter adapter;
    RecyclerView rcvChapterDetail;
    List<ChapterModel> modelList = new ArrayList<>();
    List<ChapterModel> modelListChap = new ArrayList<>();
    Toolbar toolbar;
    Button btnListChap, btnBackChap, btnNextChap;
    //save offine
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);

        //content chap
        Intent intent = getIntent();
        model = (ChapterModel) intent.getSerializableExtra("model");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(model.getStoryName());


        rcvChapterDetail = findViewById(R.id.rcvChapterDetail);
        adapter = new ChapterRecycleViewAdapter();

        rcvChapterDetail.setLayoutManager(new LinearLayoutManager(this));

        modelList.add(model);
        adapter.setData(modelList);
        rcvChapterDetail.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvChapterDetail.addItemDecoration(itemDecoration);

        //back button chap
        btnBackChap = findViewById(R.id.btnBackChap);
        //next button chap
        btnNextChap = findViewById(R.id.btnNextChap);

        // list chap button
        btnListChap = (Button) findViewById(R.id.btnListChap);
        GetListChapter();
    }

    //disable and enable button back
    private void DisAndEnaBackButton(int num){
        if(num - 2 <0){ //disable and enable button back
            btnBackChap.setEnabled(false);
            btnBackChap.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundedbuttonbackdis));
        }else {
            btnBackChap.setEnabled(true);
            btnBackChap.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundedbuttonback));
        }
    }

    //disable and enable button next
    private void DisAndEnaNextButton(int num, int numMax){
        if(num == numMax){ //disable and enable button back
            btnNextChap.setEnabled(false);
            btnNextChap.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundedbuttonnextdis));
        }else {
            btnNextChap.setEnabled(true);
            btnNextChap.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundedbuttonnext));
        }
    }

    //
    // get list chapter
    private void GetListChapter(){

        Methods methods = getRetrofit().create(Methods.class);
        Call<List<ChapterModel>> call = methods.getChapter(model.getStoryId());
        call.enqueue(new Callback<List<ChapterModel>>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<List<ChapterModel>> call, Response<List<ChapterModel>> response) {
                List<ChapterModel> list = (ArrayList<ChapterModel>) response.body();

                //disable and enable button back
                DisAndEnaBackButton(model.getNumberChapter());

                //disable and enable button next
                int numMaxNext = response.body().size();
                DisAndEnaNextButton(model.getNumberChapter() ,numMaxNext);

                //back
                btnBackChap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model = response.body().get(model.getNumberChapter()-2);
                        modelList = new ArrayList<>();
                        modelList.add(model);
                        adapter.setData(modelList);
                        rcvChapterDetail.setAdapter(adapter);
                        //save offline
                        sharedPref = getSharedPreferences("ChapterSave", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();

                        //save key chapter off
                        editor.putInt(model.getStoryId(), model.getNumberChapter()).commit();

                        //disable and enable button back
                        DisAndEnaBackButton(model.getNumberChapter());
                        //disable and enable button back
                        DisAndEnaNextButton(model.getNumberChapter() ,numMaxNext);

                        btnListChap.setText("Chương " + model.getNumberChapter());
                    }
                });

                //next
                btnNextChap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model = response.body().get(model.getNumberChapter());
                        modelList = new ArrayList<>();
                        modelList.add(model);
                        adapter.setData(modelList);
                        rcvChapterDetail.setAdapter(adapter);
                        //save offline
                        sharedPref = getSharedPreferences("ChapterSave", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();

                        //save key chapter off
                        editor.putInt(model.getStoryId(), model.getNumberChapter()).commit();

                        //disable and enable button back
                        DisAndEnaBackButton(model.getNumberChapter());
                        //disable and enable button back
                        DisAndEnaNextButton(model.getNumberChapter() ,numMaxNext);

                        btnListChap.setText("Chương " + model.getNumberChapter());
                    }
                });

                //list chap
                btnListChap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creating the instance of PopupMenu
                        PopupMenu popup = new PopupMenu(ChapterDetailActivity.this, btnListChap);

                        //Inflating the Popup using xml file
                        popup.getMenuInflater()
                                .inflate(R.menu.poupup_menu, popup.getMenu());

                        for(ChapterModel i: list) {
                            // add item chap to menu
                            modelListChap.add(i);
                            popup.getMenu().add(i.getNumberChapter(), i.getNumberChapter(), i.getNumberChapter(), "Chương " + i.getNumberChapter() + ": " + i.getName());
                        }

                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(
                                        ChapterDetailActivity.this,
                                        "Chọn : " + item.getTitle(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                model = modelListChap.get(item.getItemId()-1);
                                modelList = new ArrayList<>();
                                modelList.add(model);
                                adapter.setData(modelList);
                                rcvChapterDetail.setAdapter(adapter);
                                //save offline
                                sharedPref = getSharedPreferences("ChapterSave", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();

                                //save key chapter off
                                editor.putInt(model.getStoryId(), model.getNumberChapter()).commit();

                                //disable and enable button back
                                DisAndEnaBackButton(model.getNumberChapter());
                                //disable and enable button next
                                DisAndEnaNextButton(model.getNumberChapter() ,numMaxNext);

                                btnListChap.setText("Chương " + model.getNumberChapter());
                                return true;
                            }
                        });

                        popup.show(); //showing popup menu
                    }
                }); //closing the setOnClickListener method
                btnListChap.setText("Chương " + model.getNumberChapter());
            }

            @Override
            public void onFailure(Call<List<ChapterModel>> call, Throwable t) {

            }
        });

    }

    public void goToBackChapterDetail(View view) {
        finish();
    }
}