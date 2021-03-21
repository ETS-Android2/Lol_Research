package com.example.lol_research;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewAdapterPlayer  extends RecyclerView.Adapter<RecycleViewAdapterPlayer.MyViewHolder> {
    List<Player> playerList;
    Context context;

    public RecycleViewAdapterPlayer(List<Player> playerList, Context context) {
        this.playerList = playerList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_main_item,parent, false);
        MyViewHolder holder= new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_summName.setText(playerList.get(position).getSummonerName());
        holder.tv_teamId.setText(playerList.get(position).getTeamName());

    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_summName;
        TextView tv_teamId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_summName = itemView.findViewById(R.id.tv_summName);
            tv_teamId = itemView.findViewById(R.id.tv_teamId);
        }
    }
}
