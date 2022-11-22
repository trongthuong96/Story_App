package com.example.story_app.ui.dashboard;

import static com.example.lib.RetrofitClient.getRetrofit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.StoryModel;
import com.example.lib.model.TickModel;
import com.example.story_app.Adapter.StoryAdapter;
import com.example.story_app.R;
import com.example.story_app.Session.SessionUser;
import com.example.story_app.StoryActivity;
import com.example.story_app.databinding.FragmentDashboardBinding;
import com.example.story_app.databinding.FragmentUsersBinding;
import com.example.story_app.ui.users.UsersFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private FragmentUsersBinding usersBinding;
    private SessionUser sessionUser;

    //story tick
    private StoryAdapter storyAdapter;
    private List<StoryModel> list = new ArrayList<>();
    private ListView lsvStory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sessionUser = new SessionUser(getContext());
        if(sessionUser.GetIdUser() == null){
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Thông báo!");
            alert.setIcon(R.drawable.ic_baseline_info_24);
            alert.setMessage("Đăng nhập tài khoản!");

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();
            return null;
        }else {
            DashboardViewModel dashboardViewModel =
                    new ViewModelProvider(this).get(DashboardViewModel.class);

            binding = FragmentDashboardBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            final TextView textView = binding.textDashboard;
            dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            //layout new
            storyAdapter = new StoryAdapter(getActivity(), R.layout.item_search);
            lsvStory = binding.lsvTick;
            list = new ArrayList<>();


            // go to story
            lsvStory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //model into detail story
                    StoryModel model = (StoryModel) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), StoryActivity.class);
                    intent.putExtra("model", model);
                    startActivity(intent);
                }
            });
            return root;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(new SessionUser(getActivity()).GetIdUser() != null)
        {
            if(storyAdapter != null){
                storyAdapter.clear();
            }
            GetStoryTick();
        }

    }

    //story tick
    public void GetStoryTick(){
        Methods methods = getRetrofit().create(Methods.class);
        Call<List<StoryModel>> call = methods.getTick(new SessionUser(getActivity()).GetIdUser());
        call.enqueue(new Callback<List<StoryModel>>() {
            @Override
            public void onResponse(Call<List<StoryModel>> call, Response<List<StoryModel>> response) {
                list = response.body();
                for(StoryModel i: list){
                    storyAdapter.add(i);
                }
                lsvStory.setAdapter(storyAdapter);
            }
            @Override
            public void onFailure(Call<List<StoryModel>> call, Throwable t) {
                System.out.println();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}