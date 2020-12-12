package com.example.instaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter <PostAdapter.Holder> {

    List<Post> postList;
    Context context;
    RecycleOnClickListener listener;

    public interface RecycleOnClickListener{
        void onItemClick (int position);
    }

    public PostAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    public void setOnItemClickListener(RecycleOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_card, parent, false);
        return new Holder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.Holder holder, int position) {
        holder.card_location.setText(postList.get(position).getLocation());
        holder.card_description.setText(postList.get(position).getDescription());
        holder.card_title.setText(postList.get(position).getTitle());
        Picasso.get().load(postList.get(position).getImageUrl()).into(holder.card_image);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        TextView card_location, card_title, card_description;
        ImageView card_image;

        public Holder(@NonNull View itemView, final RecycleOnClickListener listener) {
            super(itemView);
            card_location = itemView.findViewById(R.id.card_location);
            card_title = itemView.findViewById(R.id.card_title);
            card_description = itemView.findViewById(R.id.card_description);
            card_image = itemView.findViewById(R.id.card_image);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.onItemClick(position);
            });
        }
    }
}
