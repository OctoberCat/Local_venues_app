package com.localvenues.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.localvenues.R;
import com.localvenues.model.venueResponse.Venue;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VenuesRecyclerAdapter extends RecyclerView.Adapter<VenuesRecyclerAdapter.VenueViewHolder> {

    private List<Venue> venueList;

    public VenuesRecyclerAdapter(List<Venue> venueList) {
        this.venueList = venueList;
    }

    @Override

    public VenuesRecyclerAdapter.VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VenuesRecyclerAdapter.VenueViewHolder holder, int position) {
        Venue venue = venueList.get(position);
        String url = venue.getFeaturedPhotos().getItems().get(0).getPrefix()
                + "100x100" +
                venue.getFeaturedPhotos().getItems().get(0).getSuffix();
        Uri uri = Uri.parse(url);
        String color = "#" + venue.getRatingColor();
        holder.venueName.setText(venue.getName());
        holder.phone.setText(venue.getContact().getFormattedPhone());
        holder.address.setText(venue.getLocation().getAddress());
        holder.rating.setText(venue.getRating().toString());
        //
        holder.rating.setTextColor(Color.parseColor(color));
        //
        Context context = holder.photo.getContext();
        Picasso.with(context).load(uri).into(holder
                .photo);
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    public void updateData(List<Venue> venues) {
        Log.i("updateData", "WOW I'M UPDATED");
        venueList.clear();
        venueList.addAll(venues);
        notifyDataSetChanged();
    }


    public class VenueViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.photo)
        ImageView photo;
        @Bind(R.id.venue_name)
        TextView venueName;
        @Bind(R.id.phone)
        TextView phone;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.rating)
        TextView rating;

        public VenueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
