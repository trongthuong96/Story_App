package com.example.story_app;

import static com.example.lib.RetrofitClient.getRetrofit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.ChapterModel;
import com.example.lib.model.CommentModel;
import com.example.lib.model.StoryModel;
import com.example.story_app.Adapter.ChapterAdapter;
import com.example.story_app.Adapter.CommentAdapter;
import com.example.story_app.Session.PositionSession;
import com.example.story_app.Session.SessionUser;
import com.example.story_app.Utility.SD;
import com.example.story_app.databinding.ActivityCommentBinding;
import com.example.story_app.databinding.ActivityMainBinding;
import com.example.story_app.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    private StoryModel model;
    private ActivityCommentBinding binding;

    //Chapter
    CommentAdapter commentAdapter;
    List<CommentModel> list = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView lsvComment;
    EditText edtContentComment;
    CommentModel commentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_comment);

        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // lấy biến từ intent story
        Intent intent = getIntent();
        model = (StoryModel) intent.getSerializableExtra("modelStory");

        final Toolbar toolbarNameStoryComment = binding.toolbarNameStoryComment;
        toolbarNameStoryComment.setTitle("Bình luận truyện: " + model.getName());

        //comment
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        commentAdapter = new CommentAdapter(this, R.layout.item_comment);
        lsvComment = binding.lsvComment;
        edtContentComment = binding.edtComment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetComments();
    }

    // get list comment
    public void GetComments(){

        Methods methods = getRetrofit().create(Methods.class);
        Call<List<CommentModel>> call = methods.getComments(model.getId());
        call.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {

                list = response.body();
                commentAdapter.clear();
                lsvComment.setAdapter(null);

                if(list != null){
                    for(CommentModel i: list){
                        commentAdapter.add(i);
                    }
                    lsvComment.setAdapter(commentAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {

            }
        });

    }

    // back story
    public void goToBackStoryActivity(View view) {
        finish();
    }

    // post comment
    public void postComment(View view) {

        if(new SessionUser(getApplicationContext()).GetIdUser() == null)
        {
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
            return;
        }

        //content comment
        commentModel = new CommentModel();
        String userId = new SessionUser(getApplicationContext()).GetIdUser();
        commentModel.setUserId(userId);
        commentModel.setContent(edtContentComment.getText().toString());
        commentModel.setStoryId(model.getId());

        Methods methods = getRetrofit().create(Methods.class);
        Call<CommentModel> call = methods.postComment(commentModel);
        call.enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                if (response.code() == 200){
                    onResume();
                    edtContentComment.getText().clear();
                }
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {

            }
        });
    }
}