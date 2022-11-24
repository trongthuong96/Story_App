package com.example.story_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib.model.ChapterModel;
import com.example.lib.model.CommentModel;
import com.example.story_app.R;
import com.example.story_app.Session.SessionUser;

public class CommentAdapter extends ArrayAdapter<CommentModel> {
    Activity context;
    int resource;

    public CommentAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = (Activity) context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View view = layoutInflater.inflate(this.resource, null);

        CommentModel model = getItem(position);

        if(this.resource == R.layout.item_comment){
            //khai bao
            TextView txtNameStoryComment = view.findViewById(R.id.txtCommentNameUser);
            TextView txtContentComment = view.findViewById(R.id.txtCommentContent);
            TextView txtCreateDateComment = view.findViewById(R.id.txtCreateDateComment);

            //gan gia tri
            txtNameStoryComment.setText(model.getUserName());
            txtContentComment.setText(model.getContent());
            txtCreateDateComment.setText(model.getCreateDate());
        }
        return view;
    }

}
