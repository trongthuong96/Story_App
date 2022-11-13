package com.example.story_app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.story_app.R;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final ArrayList<SlideModel> imageList;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Truyện mới cập nhập");

        //add
        imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.slide1, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slide2, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slide3, ScaleTypes.FIT));
    }

    public LiveData<String> getText() {
        return mText;
    }
    public ArrayList<SlideModel> getSlide() {
        return imageList;
    }

}