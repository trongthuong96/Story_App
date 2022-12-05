package com.example.story_app.Adapter;

import static com.example.lib.RetrofitClient.getRetrofit;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.ListViewAutoScrollHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.CategoryModel;
import com.example.lib.model.CommentModel;
import com.example.story_app.CommentActivity;
import com.example.story_app.R;
import com.example.story_app.Session.PositionSession;
import com.example.story_app.Session.SessionUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends ArrayAdapter<CommentModel> {
    Activity context;
    int resource;
    private List<CommentModel> modelList = new ArrayList<>();

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
            ImageButton imgDeleteComment = view.findViewById(R.id.img_deleteComment);

            //gan gia tri
            txtNameStoryComment.setText(model.getUserName());
            txtContentComment.setText(model.getContent());
            txtCreateDateComment.setText(model.getCreateDate());
            String userId = new SessionUser(getContext()).GetIdUser();
            if(model.getUserId().equals(userId) ){
                imgDeleteComment.setVisibility(View.VISIBLE);
            }

            // delete comment
            imgDeleteComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder adb=new AlertDialog.Builder(context);
                    adb.setTitle("Xóa?");
                    adb.setMessage("Bạn có muốn xóa bình luận? ");
                    final int positionToRemove = position;
                    adb.setNegativeButton("Hủy", null);
                    adb.setPositiveButton("Đồng ý", new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Methods methods = getRetrofit().create(Methods.class);
                            Call<String> call = methods.deleteComment(model.getId());
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if(response.code() == 200){
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        }
                    });
                    adb.show();
                }
            });
        }
        return view;
    }

}
