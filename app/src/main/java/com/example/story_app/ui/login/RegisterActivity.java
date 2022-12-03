package com.example.story_app.ui.login;

import static com.example.lib.RetrofitClient.getRetrofit;
import static com.example.story_app.Utility.SD.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.UserModel;
import com.example.story_app.MainActivity;
import com.example.story_app.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private EditText edtEmail, edtPassword, edtConfirmPass, edtUsername;
    private TextView txtNot;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        edtEmail = binding.edtEmailRegister;
        edtPassword = binding.passwordRegister;
        edtConfirmPass = binding.passwordRegisterCon;
        edtUsername = binding.edtUsernameRegister;
        txtNot = binding.notiErrorRegister;
        btnRegister = binding.btnRegister;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUsername.getText().toString() == null || edtUsername.getText().toString().equals("")){
                    txtNot.setText("Tên tài khoản không được trống!");
                    return;
                }
                if (!isValidEmail(edtEmail.getText().toString()))
                {
                    txtNot.setText("Nhập lại email!");
                    return;
                }
                else if(edtPassword.length() < 5){
                    txtNot.setText("Mật khẩu dài hơn 5 ký tự!");
                    return;
                }
                else if (!edtConfirmPass.getText().toString().equals(edtPassword.getText().toString()))
                {
                    txtNot.setText("Nhập lại mật khẩu");
                    return;
                }

                UserModel userModel = new UserModel(edtUsername.getText().toString(),edtEmail.getText().toString(), edtPassword.getText().toString());
                PostRegister(userModel);
            }
        });
    }

    private void PostRegister(UserModel userModel){
        Methods methods = getRetrofit().create(Methods.class);
        Call<UserModel> call = methods.postOneUser(userModel);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.code() == 201){
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if(response.code() == 404){
                    txtNot.setText("Đã tồn tại tên tài khoản này");
                    return;
                } else if(response.code() == 405){
                    txtNot.setText("Đã tồn tại email này");
                    return;
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
}