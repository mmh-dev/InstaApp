package com.example.instaapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.instaapp.R;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {
    TextView username, email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        username = view.findViewById(R.id.userName);
        email = view.findViewById(R.id.userEmail);

        ParseUser parseUser = ParseUser.getCurrentUser();
        username.setText(parseUser.getUsername());
        email.setText(parseUser.getEmail());

        return view;
    }
}