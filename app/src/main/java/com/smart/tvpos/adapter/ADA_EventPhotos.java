package com.smart.tvpos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.smart.tvpos.R;
import com.smart.tvpos.global.API;

import java.util.List;

public class ADA_EventPhotos extends RecyclerView.Adapter<ADA_EventPhotos.ViewHolder> {

    private List<String> photoList;
    private Context mContext;

    public ADA_EventPhotos(List<String> photoList, Context mContext) {
        this.photoList = photoList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder= new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_photo, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH);
        Glide.with(mContext)
                .load(API.IMG_SERVER_IP + "/" + photoList.get(position))
                .apply(requestOptions)
                .into(holder.photoView);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoView;

        public ViewHolder(View itemView) {
            super(itemView);
            photoView = (ImageView) itemView.findViewById(R.id.img_event_photo);
        }
    }
}
