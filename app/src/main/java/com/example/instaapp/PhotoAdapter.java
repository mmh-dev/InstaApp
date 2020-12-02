package com.example.instaapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter <PhotoAdapter.Holder> {

    List<Bitmap> photosList;
    Context context;
    RecycleOnClickListener listener;

    public interface RecycleOnClickListener{
        void onItemClick (int position);
    }

    public PhotoAdapter(List<Bitmap> photosList, Context context) {
        this.photosList = photosList;
        this.context = context;
    }

    public void setOnItemClickListener (RecycleOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhotoAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_card, parent, false);
        return new Holder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.Holder holder, int position) {
        holder.photo.setImageBitmap(photosList.get(position));
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView photo;


        public Holder(@NonNull View itemView, final RecycleOnClickListener listener) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = Holder.this.getAdapterPosition();
                    listener.onItemClick(position);
                }
            });
        }
    }
}
