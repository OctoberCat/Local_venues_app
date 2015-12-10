package com.forsquare_android_vternovoi.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forsquare_android_vternovoi.R;
import com.forsquare_android_vternovoi.models.Item;
import com.forsquare_android_vternovoi.models.Venue;
import com.forsquare_android_vternovoi.models.VenueResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by valentin on 10.12.15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private List<Item> itemList;


    public RecyclerAdapter(VenueResponse results) {//retrieve itemList only, think about it

/*        List<Group>  groupList = results.getResponse().getGroups();
        Group group =  groupList.get(0);
        List<Item> items = group.getItems();*/
        this.itemList = results.getResponse().getGroups().get(0).getItems();

        for (Item item : itemList) {
            Log.i("AdapterConstr", "items" + item.getVenue().getName());
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Venue venue = itemList.get(position).getVenue();
        holder.name.setText(venue.getName());
        holder.address.setText(venue.getLocation().getAddress());
        holder.rating.setText(venue.getRating().toString());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.venueName)
        TextView name;
        @Bind(R.id.streetAdr)
        TextView address;
        @Bind(R.id.rating)
        TextView rating;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
