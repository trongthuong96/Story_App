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
import androidx.recyclerview.widget.RecyclerView;

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

    private class ViewHolderItemStory{
        TextView nameStory, nameCagory;
        ImageView imageView;
    }

    private class ViewHolderItemSearch{
        TextView nameStory, author, status, summary;
        ImageView imageView;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHolderItemStory viewHolder = null;
        ViewHolderItemSearch holderItemSearch = null;

        if(view == null) {
            LayoutInflater layoutInflater = this.context.getLayoutInflater();
            view = layoutInflater.inflate(this.resource, null);

            if (this.resource == R.layout.item_story) {
                viewHolder = new ViewHolderItemStory();
                // create value
                viewHolder.nameStory = view.findViewById(R.id.txtNameStory);
                viewHolder.nameCagory = view.findViewById(R.id.txtCagory);
                viewHolder.imageView = view.findViewById(R.id.imgImageStory);

                view.setTag(viewHolder);
            } else if (this.resource == R.layout.item_search) {
                holderItemSearch = new ViewHolderItemSearch();
                //
                holderItemSearch.nameStory = view.findViewById(R.id.txtNameSearchItem);
                holderItemSearch.author = view.findViewById(R.id.txtAuthorSearchItem);
                holderItemSearch.status = view.findViewById(R.id.txtNumChapSearchItem);
                holderItemSearch.summary = view.findViewById(R.id.txtSummarySearchItem);
                holderItemSearch.imageView = view.findViewById(R.id.imgSearchItem);

                view.setTag(holderItemSearch);
            }
        }else {
            if (this.resource == R.layout.item_story){
                viewHolder = (ViewHolderItemStory) view.getTag();
            }else if (this.resource == R.layout.item_search) {
                holderItemSearch = (ViewHolderItemSearch) view.getTag();
            }
        }

        StoryModel model = getItem(position);

        if(this.resource == R.layout.item_story) {

            //add view
            viewHolder.nameStory.setText(model.getName());
            viewHolder.nameCagory.setText(model.getCategoryName().get(0));
            Glide.with(this.context).load(model.getImage()).into(viewHolder.imageView);
        }else if(this.resource == R.layout.item_search){

            //add view
            holderItemSearch.nameStory.setText(model.getName());
            holderItemSearch.author.setText("Tác giả: " + model.getAuthorName());
            holderItemSearch.status.setText("Trạng thái: " + model.getStatus());
            holderItemSearch.summary.setText("Tóm tắt: " + Html.fromHtml(model.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
            Glide.with(this.context).load(model.getImage()).into(holderItemSearch.imageView);

        }
        return view;
    }
}
