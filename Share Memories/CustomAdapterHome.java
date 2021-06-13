package com.tanyadas.wedmist.shareMemories;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapterHome extends RecyclerView.Adapter<CustomAdapterHome.MyViewHolder>
{
    private Context context;
    private List<CustomHome> postList;

    public CustomAdapterHome(Context context, List<CustomHome> postList) {
        this.context = context;
        this.postList = postList;
    }


    @NonNull
    @Override
    public CustomAdapterHome.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_home_display_images, null);
        CustomAdapterHome.MyViewHolder holder = new CustomAdapterHome.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterHome.MyViewHolder holder, int position) {
        CustomHome customHome = postList.get(position);
        holder.userNameHome.setText(customHome.getUsername());
        holder.userNameContent.setText(customHome.getUserNameContent());
        holder.imageView.setImageURI(Uri.parse(customHome.getImage()));
        holder.captionHome.setText(customHome.getCaption());
        holder.hastagHome.setText(customHome.getHastag());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView captionHome, hastagHome, userNameHome, userNameContent;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            captionHome = itemView.findViewById(R.id.caption_home);
            userNameContent = itemView.findViewById(R.id.username_in_post_content);
            hastagHome = itemView.findViewById(R.id.hastag_home);
            userNameHome = itemView.findViewById(R.id.username_home);
            imageView = itemView.findViewById(R.id.display_image);

        }
    }
}
