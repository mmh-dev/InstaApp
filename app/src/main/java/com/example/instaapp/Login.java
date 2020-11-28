package com.example.instaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    MaterialEditText user_name, user_password;
    Button login_btn;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_name = findViewById(R.id.user_name_login);
        user_password = findViewById(R.id.user_password_login);
        login_btn = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signup);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = user_name.getText().toString();
                String txt_password = user_password.getText().toString();
                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Login.this, "Complete all fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    login(txt_username, txt_password);
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void login(String txt_username, String txt_password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://parseapi.back4app.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<User> call = api.loggingIn(1, txt_username, txt_password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(Login.this, "User is Logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });
    }
}