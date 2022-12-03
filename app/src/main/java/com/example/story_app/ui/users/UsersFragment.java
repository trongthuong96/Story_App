package com.example.story_app.ui.users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.story_app.Session.SessionUser;
import com.example.story_app.databinding.FragmentUsersBinding;
import com.example.story_app.ui.login.LoginActivity;
import com.example.story_app.ui.login.RegisterActivity;

public class UsersFragment extends Fragment {

    private FragmentUsersBinding binding;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UsersViewModel notificationsViewModel =
                new ViewModelProvider(this).get(UsersViewModel.class);

        binding = FragmentUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //button login
        final Button buttonLogin = binding.btnLogin;

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                //intent.putExtra("modelStory", model);
                startActivity(intent);
            }
        });

        //button register
        final Button buttonRegister = binding.btnRegister;

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        final TextView textNameUser = binding.txtNameUser;
        final TextView txtEmailUser = binding.txtEmailUser;
        final Button buttonLogout = binding.btnLogout;

        SessionUser sessionUser = new SessionUser(getContext());
        if (sessionUser.isLoggedIn()){
            buttonLogin.setVisibility(View.INVISIBLE);
            buttonRegister.setVisibility(View.INVISIBLE);

            textNameUser.setVisibility(View.VISIBLE);
            textNameUser.setText("Tên tài khoản: " + sessionUser.GetNameUser());
            txtEmailUser.setVisibility(View.VISIBLE);
            txtEmailUser.setText("Email: " + sessionUser.GetEmailUser());
            buttonLogout.setVisibility(View.VISIBLE);

        } else {
            buttonLogin.setVisibility(View.VISIBLE);
            buttonRegister.setVisibility(View.VISIBLE);

            textNameUser.setVisibility(View.INVISIBLE);
            txtEmailUser.setVisibility(View.INVISIBLE);
            buttonLogout.setVisibility(View.INVISIBLE);
        }

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionUser.logoutUser();
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