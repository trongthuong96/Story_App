package com.example.story_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;

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
            nameCagory.setText(model.getCategoryName().get(0));
            Glide.with(this.context).load(model.getImage()).into(imageView);
        }else if(this.resource == R.layout.item_search){

            //
            nameStory = view.findViewById(R.id.txtNameSearchItem);
            TextView author = view.findViewById(R.id.txtAuthorSearchItem);
            TextView status = view.findViewById(R.id.txtNumChapSearchItem);
            TextView summary = view.findViewById(R.id.txtSummarySearchItem);
            imageView = view.findViewById(R.id.imgSearchItem);

            //add view
            StoryModel model = getItem(position);
            nameStory.setText(model.getName());
            author.setText("Tác giả: " + model.getAuthorName());
            status.setText("Trạng thái: " + model.getStatus());
            summary.setText("Tóm tắt: " + Html.fromHtml(model.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
            Glide.with(this.context).load(model.getImage()).into(imageView);

        }
        return view;
    }
}
