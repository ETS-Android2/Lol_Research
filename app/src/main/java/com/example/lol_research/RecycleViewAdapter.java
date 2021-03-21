package com.example.lol_research;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Project "Lol_Research" created by Vincent-BERNET | Némésis on 21/03/2021,
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    List<Match> matchList;
    Context context;

    public RecycleViewAdapter(List<Match> matchList, Context context) {
        this.matchList = matchList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_match,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_Queue.setText(matchList.get(position).getQueue());
        holder.tv_role.setText(matchList.get(position).getLane());
        Glide.with(this.context).load(matchList.get(position).getChampionID()).into(holder.iv_championPicture);
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_championPicture;
        TextView tv_Queue;
        TextView tv_role;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_championPicture = itemView.findViewById(R.id.iv_championPicture);
            tv_Queue  = itemView.findViewById(R.id.tv_Queue);
            tv_role  = itemView.findViewById(R.id.tv_role);
        }
    }
}
