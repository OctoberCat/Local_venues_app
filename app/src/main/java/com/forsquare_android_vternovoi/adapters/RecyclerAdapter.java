package com.forsquare_android_vternovoi.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forsquare_android_vternovoi.R;
import com.forsquare_android_vternovoi.models.Venue;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by valentin on 10.12.15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    List<Venue> venueList;


    public RecyclerAdapter(List<Venue> venueList) {
        this.venueList = venueList;
        for (Venue venue : venueList) {
            Log.i("AdapterConstr", "venues name test: " + venue.getName());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Venue venue = venueList.get(position);
        holder.venueName.setText(venue.getName());
        holder.streetAdr.setText(venue.getLocation().getAddress());
        holder.rating.setText(venue.getRating().toString());
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    public void updateData(List<Venue> updateList) {//todo update data
        venueList.clear();
        venueList.addAll(updateList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.venuePhoto)
        ImageView venuePhoto;
        @Bind(R.id.venueName)
        TextView venueName;
        @Bind(R.id.streetAdr)
        TextView streetAdr;
        @Bind(R.id.rating)
        TextView rating;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
