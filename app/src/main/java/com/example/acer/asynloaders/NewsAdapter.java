package com.example.acer.asynloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    Context context;
    ArrayList<NewsModel> newsModels;
    public NewsAdapter(MainActivity mainActivity, ArrayList<NewsModel> newsModels) {
        this.context=mainActivity;
        this.newsModels=newsModels;
    }

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.recyclerlayout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, int position) {

        NewsModel model=newsModels.get(position);
       //holder.textView.setText(model.getJobj());
       holder.textView.setText(model.getTitle());
        Picasso.with(context).load(model.imgurl).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.jobj);
            //textView1=imageView.findViewById(R.id.title);
            imageView=itemView.findViewById(R.id.imgurl);
        }
    }
}
