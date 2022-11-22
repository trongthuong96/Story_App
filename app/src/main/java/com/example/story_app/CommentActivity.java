package com.example.story_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lib.model.StoryModel;
import com.example.story_app.databinding.ActivityCommentBinding;
import com.example.story_app.databinding.ActivityMainBinding;
import com.example.story_app.databinding.FragmentHomeBinding;

public class CommentActivity extends AppCompatActivity {
    private StoryModel model;
    private ActivityCommentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_comment);

        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // lấy biến từ intent story
        Intent intent = getIntent();
        model = (StoryModel) intent.getSerializableExtra("modelStory");

        final TextView txtStoryNameCommnet = binding.txtNameStoryComment;
        txtStoryNameCommnet.setText("Bình luận truyện: " + model.getName());
    }
}