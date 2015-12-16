package com.forsquare_android_vternovoi.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.forsquare_android_vternovoi.R;
import com.forsquare_android_vternovoi.models.Venue;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by valentin on 10.12.15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROGRESS = 0;

    private List<Venue> venueList;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public RecyclerAdapter(List<Venue> venueList, RecyclerView recyclerView) {
        this.venueList = venueList;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_item, parent, false);
            viewHolder = new ItemViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);

            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Venue venue = venueList.get(position);
            ((ItemViewHolder) holder).venueName.setText(venue.getName());
            ((ItemViewHolder) holder).streetAdr.setText(venue.getLocation().getAddress());
            ((ItemViewHolder) holder).rating.setText(venue.getRating().toString());
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
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

    @Override
    public int getItemViewType(int position) {
        return venueList.get(position) != null ? VIEW_ITEM : VIEW_PROGRESS;
    }

    ///////////////

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.venuePhoto)
        ImageView venuePhoto;
        @Bind(R.id.venueName)
        TextView venueName;
        @Bind(R.id.streetAdr)
        TextView streetAdr;
        @Bind(R.id.rating)
        TextView rating;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.progressBar1)
        ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
