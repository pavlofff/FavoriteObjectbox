package com.example.pavel.favoriteobjectbox;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class StationAdapter extends  RecyclerView.Adapter<StationAdapter.ViewHolder>{

    ArrayList<Station> stations;

    public StationAdapter(ArrayList<Station> stations) {
        this.stations = stations;
    }

    @Override
    public StationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(StationAdapter.ViewHolder holder, int position) {
        Station station = stations.get(position);
        holder.name.setText(station.getName());
        // Надпись на кнопке избранного
        // Если не в избранном, то "добавить". Если в избранном, то "убрать"
        String buttonText = station.isFavorite()? "Delete favorite": "Add favorite";
        holder.buttonFavorite.setText(buttonText);
        // отправляем в активити ID станции, на которой нажали кнопку избранного
        holder.buttonFavorite.setTag(station.getId());
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public void notifyList(ArrayList<Station> stations){
        this.stations = stations;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final Button buttonFavorite;
        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            buttonFavorite = view.findViewById(R.id.buttonFavorite);
        }
    }
}
