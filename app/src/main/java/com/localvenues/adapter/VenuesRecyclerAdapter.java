package com.localvenues.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.localvenues.ui.activity.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VenuesRecyclerAdapter extends RecyclerView.Adapter<VenuesRecyclerAdapter.VenueViewHolder> {

    private List<Venue> venueList;

    public VenuesRecyclerAdapter(List<Venue> venueList) {
        this.venueList = venueList;
    }

    private static final String LOG_TAG = VenuesRecyclerAdapter.class.getSimpleName();

    @Override

    public VenuesRecyclerAdapter.VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VenuesRecyclerAdapter.VenueViewHolder holder, int position) {
        holder.venue = venueList.get(position);
        String url = holder.venue.getFeaturedPhotos().getItems().get(0).getPrefix()
                + "100x100" +
                holder.venue.getFeaturedPhotos().getItems().get(0).getSuffix();
        Uri uri = Uri.parse(url);
        String color = "#" + holder.venue.getRatingColor();
        holder.venueName.setText(holder.venue.getName());
        holder.phone.setText(holder.venue.getContact().getFormattedPhone());
        holder.address.setText(holder.venue.getLocation().getAddress());
        holder.rating.setText(holder.venue.getRating().toString());
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
        venueList.clear();
        venueList.addAll(venues);
        notifyDataSetChanged();
        Log.i(LOG_TAG, "WOW I'M UPDATED");
    }


    public class VenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        Venue venue;

        private final String TAG = VenueViewHolder.class.getSimpleName();

        public VenueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

/*            itemView.setOnClickListener(
                    v -> {
                Log.i(TAG, "VenueViewHolder: Retrolambda is alive!  ");
                Log.i(TAG, "VenueViewHolder: venue name:" + venue.getName());
            });*/


        }


        @Override
        public void onClick(View v) {
            Log.i(TAG, "VenueViewHolder: venue name:" + venue.getName());
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.DETAILS_KEY, venue.getId());
            v.getContext().startActivity(intent);
        }
    }
}
