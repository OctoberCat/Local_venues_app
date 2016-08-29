package com.localvenues.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.localvenues.R;
import com.localvenues.model.tipResponse.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * Created by OctoberCat on 18.07.16.
 */
public class TipsRecyclerAdapter extends RecyclerView.Adapter<TipsRecyclerAdapter.TipViewHolder> {
    private List<Item> tipsList;

    public TipsRecyclerAdapter(List<Item> tipsList) {
        this.tipsList = tipsList;
    }

    @Override
    public TipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tip, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TipViewHolder holder, int position) {
        holder.tip = tipsList.get(position);
        String fullName;
        /*if (holder.tip.getUser().getLastName().equals(null)) {
            fullName = holder.tip.getUser().getFirstName();
        } else {
            fullName = holder.tip.getUser().getFirstName() + " " + holder.tip.getUser().getLastName();
        }*/
        fullName = holder.tip.getUser().getFirstName() + " " + holder.tip.getUser().getLastName();
        holder.authorName.setText(fullName);
        String userPhotoUrl = holder.tip.getUser().getPhoto().getPrefix() + "100x100" + holder.tip.getUser().getPhoto().getSuffix();
        Uri uri = Uri.parse(userPhotoUrl);
        Picasso.with(holder.authorPhoto.getContext()).load(uri).into(holder.authorPhoto);

        holder.tipText.setText(holder.tip.getText());
    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    public void updateData(List<Item> tips) {
        tipsList.clear();
        tipsList.addAll(tips);
        notifyDataSetChanged();
        Log.i("Update data", "updateData:  updated");
    }

    public class TipViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.author_name)
        TextView authorName;
        @Bind(R.id.author_photo)
        ImageView authorPhoto;
        @Bind(R.id.tip_text)
        TextView tipText;

        Item tip;

        public TipViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
