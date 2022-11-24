package com.example.story_app.ui.login;

import static com.example.lib.RetrofitClient.getRetrofit;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.UserModel;
import com.example.story_app.MainActivity;
import com.example.story_app.R;
import com.example.story_app.Session.SessionUser;
import com.example.story_app.Utility.SD;
import com.example.story_app.databinding.ActivityLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionUser sessionUser;
    private EditText messageError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.btnActivitylogin;
        final ProgressBar loadingProgressBar = binding.loading;
        messageError = binding.notiError;

        sessionUser = new SessionUser(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel = new UserModel();
                userModel.setEmail(usernameEditText.getText().toString());
                userModel.setPassword(passwordEditText.getText().toString());
                loginUser(userModel);
            }
        });
    }

    //get user
    private void getUsers(){
        Methods methods = getRetrofit().create(Methods.class);
        Call<List<UserModel>> call = methods.getUsers();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                response.body();
                return;
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

    //check login
    private void loginUser(UserModel userModel){
        Methods methods = getRetrofit().create(Methods.class);
        Call<UserModel> call = methods.postUser(userModel);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.body()!=null){
                    UserModel model = response.body();
                    sessionUser.createLoginSession(model.getUserName(), model.getEmail(), SD.Role_User_Indi, model.getId());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    intent.putExtra("modelUser", model);
                    startActivity(intent);
                }
                else if(response.code() == 401){
                    messageError.setText("Tài khoản đăng nhập sai!");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                t.toString();
                return;
            }
        });
    }
}