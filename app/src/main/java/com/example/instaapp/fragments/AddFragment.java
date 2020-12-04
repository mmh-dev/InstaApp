package com.example.instaapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instaapp.PhotoAdapter;
import com.example.instaapp.R;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class AddFragment extends Fragment {

    List<Bitmap> photosList = new ArrayList<>();
    RecyclerView recyclerView;
    private static final int IMAGE_REQUEST = 1;
    private Uri selectedImage;
    PhotoAdapter adapter;
    Button uploadPhotoBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        uploadPhotoBtn = view.findViewById(R.id.upload_photo_btn);
        recyclerView = view.findViewById(R.id.gallery_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new PhotoAdapter(photosList, getContext());

        uploadPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                photosList.add(bitmap);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                ParseFile parseFile = new ParseFile("image.png", byteArray);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getActivity(), "Image is saved!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ParseObject object = new ParseObject("Photo");
                object.put("title", "Picture 1");
                object.put("image", parseFile);
                object.put("userId", ParseUser.getCurrentUser().getUsername());
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getActivity(), "Object is saved!", Toast.LENGTH_SHORT).show();
//                            retrieveObject();
                        } else {
                            Log.i("error", ""+ e.getMessage());
                        }
                    }
                });
            } catch (IOException e) {
                Log.i("error", "" + e.getMessage());
            }
        }
    }

    private void retrieveObject() {
        ParseQuery<ParseObject> query  = new ParseQuery<ParseObject>("Photo");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object: objects) {
                    ParseFile file = (ParseFile) object.get("image");
                    file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if(e == null && data != null){
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                            } else {
                                Toast.makeText(getContext(), "Error in downloading a data!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void uploadObject(ParseFile parseFile) {

    }
}