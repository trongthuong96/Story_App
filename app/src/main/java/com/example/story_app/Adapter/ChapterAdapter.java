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
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.model.ChapterModel;
import com.example.story_app.R;

public class ChapterAdapter extends ArrayAdapter<ChapterModel> {

    Activity context;
    int resource;

    public ChapterAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = (Activity) context;
        this.resource = resource;
    }

    private class ViewHolderActiChap{
        TextView txtChapterItem;
    }

    private class ViewHolderItemChap
    {
        TextView txtChapterNameNumber, txtChapterContent;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        //viewholder
        ViewHolderActiChap holderActiChap = null;
        ViewHolderItemChap holderItemChap = null;

        if(view == null){
            LayoutInflater layoutInflater = this.context.getLayoutInflater();
            view = layoutInflater.inflate(this.resource, null);

            if(this.resource == R.layout.item_chapter_activity_chapter) {
                //khai bao
                holderActiChap = new ViewHolderActiChap();
                holderActiChap.txtChapterItem = view.findViewById(R.id.txtChapterItem);

                //set hol
                view.setTag(holderActiChap);
            }else if (this.resource == R.layout.item_chapter_acitivity_chapter_detail){
                holderItemChap = new ViewHolderItemChap();

                holderItemChap.txtChapterNameNumber = view.findViewById(R.id.txtChapterNameNumber);
                holderItemChap.txtChapterContent = view.findViewById(R.id.txtChapterContent);

                //
                view.setTag(holderItemChap);
            }
        }else {
            if(this.resource == R.layout.item_chapter_activity_chapter){
                holderActiChap = (ViewHolderActiChap) view.getTag();
            }else if (this.resource == R.layout.item_chapter_acitivity_chapter_detail){
                holderItemChap = (ViewHolderItemChap) view.getTag();
            }
        }


        ChapterModel model = getItem(position);

        if(this.resource == R.layout.item_chapter_activity_chapter){

            //gan gia tri
            holderActiChap.txtChapterItem.setText("Chương "+ model.getNumberChapter() + ": " + model.getName());

            //chapter of story read
            SharedPreferences sharedPref = this.context.getSharedPreferences("ChapterSave", context.MODE_PRIVATE);
            int chapterNumber = sharedPref.getInt(model.getStoryId(),-1);
            if(chapterNumber == model.getNumberChapter()){
                holderActiChap.txtChapterItem.setTypeface(Typeface.DEFAULT_BOLD);
            }
        }else if (this.resource == R.layout.item_chapter_acitivity_chapter_detail){

            holderItemChap.txtChapterNameNumber.setText("Chương "+ model.getNumberChapter() + ": " + model.getName());
            holderItemChap.txtChapterContent.setText(model.getContent());

        } /*else if(this.resource == R.layout.admin_item_list_chater){
            TextView name = view.findViewById(R.id.txtAdminChapterNameItem);
            TextView dateCrate = view.findViewById(R.id.txtAdminChapterDateItem);
            TextView numberChap = view.findViewById(R.id.txtAdminChapterNumberItem);
            CheckBox cbChapterDeleteItem = view.findViewById(R.id.cbChapterDeleteItem);

            //format date
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatter.format(model.getDateCreate());
            dateCrate.setText(today);

            //set name
            name.setText(model.getName());
            numberChap.setText(model.getChapterNumber().toString());

            //delete
            //check box delete
            cbChapterDeleteItem.setTag(position);
            cbChapterDeleteItem.setChecked(chapterNumber.contains(model.getChapterNumber()));
            cbChapterDeleteItem.setChecked(modelList.contains(model));
            cbChapterDeleteItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (!compoundButton.isChecked()) {
                        chapterNumber.remove(model.getChapterNumber());
                        modelList.remove(model);
                    } else if (compoundButton.isChecked()) {
                        if (!chapterNumber.contains(model.getChapterNumber())) {
                            chapterNumber.add(model.getChapterNumber());
                            modelList.add(model);
                        }
                    }
                }
            });
        }*/
        return view;
    }
}
