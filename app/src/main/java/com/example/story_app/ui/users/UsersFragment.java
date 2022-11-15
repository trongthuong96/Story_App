package com.example.story_app.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.story_app.ChapterActivity;
import com.example.story_app.databinding.FragmentUsersBinding;
import com.example.story_app.ui.login.LoginActivity;

public class UsersFragment extends Fragment {

    private FragmentUsersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UsersViewModel notificationsViewModel =
                new ViewModelProvider(this).get(UsersViewModel.class);

        binding = FragmentUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //button login
        final Button buttonLogin = binding.button;

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                //intent.putExtra("modelStory", model);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}