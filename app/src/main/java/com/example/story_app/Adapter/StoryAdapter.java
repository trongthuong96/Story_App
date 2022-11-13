package com.example.story_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.lib.model.StoryModel;
import com.example.story_app.R;
import com.example.story_app.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class StoryAdapter extends ArrayAdapter<StoryModel> {

    Activity context;
    int resource;

    private ArrayList<String> storyIds = new ArrayList<>();
    private List<StoryModel> modelList = new ArrayList<>();

    public StoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = (Activity)context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View view = layoutInflater.inflate(this.resource, null);

        TextView nameStory;
        ImageView imageView;

        if(this.resource == R.layout.item_story) {
            // create value
            nameStory = view.findViewById(R.id.txtNameStory);
            TextView nameCagory = view.findViewById(R.id.txtCagory);
            imageView = view.findViewById(R.id.imgImageStory);

            //add view
            StoryModel model = getItem(position);
            nameStory.setText(model.getName());
            nameCagory.setText(model.getUpdateDate().toString());
            Glide.with(this.context).load(model.getImage()).into(imageView);
        }
        return view;
    }
}
