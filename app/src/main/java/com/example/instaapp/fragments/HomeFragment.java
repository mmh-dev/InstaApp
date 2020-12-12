package com.example.instaapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instaapp.PhotoAdapter;
import com.example.instaapp.Post;
import com.example.instaapp.PostAdapter;
import com.example.instaapp.R;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    PostAdapter adapter;
    List<Post> postList = new ArrayList<>();
    SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeLayout = view.findViewById(R.id.swipeLayout);

        swipeLayout.setRefreshing(true);
        recyclerView = view.findViewById(R.id.home_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadPosts();
        swipeLayout.setOnRefreshListener(() -> {
            loadPosts();
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private void loadPosts() {
        postList.clear();
        ParseQuery<ParseObject> query = new ParseQuery("Photo");
//        query.whereEqualTo("userId", ParseUser.getCurrentUser());
//        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null){
                    for (ParseObject object: objects) {
                        Post post = new Post();
                        post.setDescription(object.getString("description"));
                        post.setTitle(object.getString("title"));
                        post.setLocation(object.getString("location"));
                        ParseFile file = object.getParseFile("image");
                        post.setImageUrl(file.getUrl());
                        postList.add(post);
                    }
                    adapter = new PostAdapter(postList, getActivity());
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        swipeLayout.setRefreshing(false);
    }
}