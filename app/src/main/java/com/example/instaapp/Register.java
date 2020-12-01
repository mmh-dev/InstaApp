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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    MaterialEditText user_name, user_email, user_password;
    Button signup_btn;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        signup_btn = findViewById(R.id.signup_btn);
        login = findViewById(R.id.login);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_username = user_name.getText().toString();
                String txt_email = user_email.getText().toString();
                String txt_password = user_password.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Register.this, "Complete all fields!", Toast.LENGTH_SHORT).show();
                }
                else if (txt_password.length() < 8){
                    Toast.makeText(Register.this, "Password must contain at least 8 symbols!", Toast.LENGTH_SHORT).show();
                }
                else if (!checkPasswordCapitals(txt_password)){
                    Toast.makeText(Register.this, "Password must contain at least 1 capital and 1 lower case letter!", Toast.LENGTH_SHORT).show();
                }
                else if (!checkPassHasNumber(txt_password)){
                    Toast.makeText(Register.this, "Password must contain at least 1 number!", Toast.LENGTH_SHORT).show();
                }
                else {
                    register(txt_username, txt_email, txt_password);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private boolean checkPassHasNumber(String password) {
        for (int i = 0; i < password.length() ; i++) {
            if (Character.isDigit(password.charAt(i))){
                return true;
            }
        }
        return false;
    }

    private boolean checkPasswordCapitals(String password) {
        boolean isCapital = false;
        boolean isLowerCase = false;
        for (int i = 0; i < password.length() ; i++) {
            if (Character.isUpperCase(password.charAt(i))){
                isCapital = true;
            }
            else if (Character.isLowerCase(password.charAt(i))){
                isLowerCase = true;
            }
        }
        return isCapital && isLowerCase;
    }

    private void register(String txt_username, String txt_email, String txt_password) {

        ParseUser user = new ParseUser();
        user.setUsername(txt_username);
        user.setPassword(txt_password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(Register.this, "User is registered!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, MainActivity.class));
                } else {
                    ParseUser.logOut();
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });



//        User user = new User(txt_username, txt_password, txt_email);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://parseapi.back4app.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        Api api = retrofit.create(Api.class);
//        Call<User> call = api.createUser(1, user);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()){
//                    Toast.makeText(Register.this, "User is registered!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(Register.this, MainActivity.class));
//                }
//            }
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.i("error", t.getMessage());
//            }
//        });
    }
}