package com.localvenues.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by valentyn on 30.05.16.
 */
public class VenuesRecyclerAdapter extends RecyclerView.Adapter<VenuesRecyclerAdapter.VenueViewHolder> {
    @Override
    public VenuesRecyclerAdapter.VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VenuesRecyclerAdapter.VenueViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class VenueViewHolder extends RecyclerView.ViewHolder {
        public VenueViewHolder(View itemView) {
            super(itemView);
        }
    }
}
