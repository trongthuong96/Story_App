package com.example.story_app.ui.home;

import static com.example.lib.RetrofitClient.getRetrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.denzcoskun.imageslider.ImageSlider;
import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.StoryModel;
import com.example.lib.model.TickModel;
import com.example.story_app.Adapter.StoryAdapter;
import com.example.story_app.R;
import com.example.story_app.Session.SessionUser;
import com.example.story_app.StoryActivity;
import com.example.story_app.databinding.FragmentHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // add
    private StoryAdapter storyAdapter;
    private List<StoryModel> list = new ArrayList<>();
    private GridView gdvStory;

    private StoryAdapter storyAdapter1;
    private GridView gdvStoryView;
    private Button tickButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //tick
        tickButton = root.findViewById(R.id.btnTick);

        //slider
        final ImageSlider imageSlider = binding.imageSlider;
        imageSlider.setImageList(homeViewModel.getSlide());

        //title
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //layout new
        storyAdapter = new StoryAdapter(getActivity(), R.layout.item_story);
        gdvStory = binding.gdvStory;
        list = new ArrayList<>();
        GetStoryNew();

        // go to story
        gdvStory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //model into detail story
                StoryModel model = (StoryModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), StoryActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });

        //layout view
        storyAdapter1 = new StoryAdapter(getActivity(), R.layout.item_story);
        gdvStoryView = binding.gdvStoryView;
        list = new ArrayList<>();
        GetStoryView();

        // go to story
        gdvStoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    //new
    public void GetStoryNew(){
        Methods methods = getRetrofit().create(Methods.class);
        Call<List<StoryModel>> call = methods.getStory();
        call.enqueue(new Callback<List<StoryModel>>() {
            @Override
            public void onResponse(Call<List<StoryModel>> call, Response<List<StoryModel>> response) {
                list = response.body();
                if(list.size()<=7)
                {
                    for(int i=0; i<list.size(); i++){
                        storyAdapter.add(list.get(i));
                    }
                }else {
                    for(int i=0; i<7; i++){
                        storyAdapter.add(list.get(i));
                    }
                }
                gdvStory.setAdapter(storyAdapter);
            }
            @Override
            public void onFailure(Call<List<StoryModel>> call, Throwable t) {
                System.out.println();
            }
        });
    }

    //view
    public void GetStoryView(){
        Methods methods = getRetrofit().create(Methods.class);
        Call<List<StoryModel>> call = methods.getStory();
        call.enqueue(new Callback<List<StoryModel>>() {
            @Override
            public void onResponse(Call<List<StoryModel>> call, Response<List<StoryModel>> response) {
                list = response.body();
                if(list.size()>0)
                {
                    for(int i=list.size()-1; i>=0; i--){
                        storyAdapter1.add(list.get(i));
                    }
                }
                gdvStoryView.setAdapter(storyAdapter1);
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