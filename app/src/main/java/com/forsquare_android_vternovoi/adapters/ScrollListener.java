package com.forsquare_android_vternovoi.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by valentin on 15.12.15.
 */
public abstract class ScrollListener extends RecyclerView.OnScrollListener {
    // item bellow position before init loading
    private int visibleThreshold = 5;
    // current offset index of data loaded
    private int currentPage = 0;
    //the total amount of items after last load
    private int previousTotalItemCount = 0;
    // flag for checking is more load needed
    private boolean loading = true;
    //the starting page index//??
    private int startingPageIndex = 0;

    private LinearLayoutManager mLayoutManager;

    public ScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLayoutManager = linearLayoutManager;
    }


    //received data comes with every scroll//
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }


        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount);
            loading = true;
        }
    }

    public abstract void onLoadMore(int page, int itemCount);
}
