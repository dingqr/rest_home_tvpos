package com.smart.tvpos.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smart.tvpos.R;

import java.util.List;

public class ADA_ChartIndicator extends RecyclerView.Adapter<ADA_ChartIndicator.ViewHolder> {

    private List<String> mIndicatorList;
    private List<Drawable> mDrawableList;

    public ADA_ChartIndicator(List<String> mIndicatorList, List<Drawable> mDrawableList) {
        this.mIndicatorList = mIndicatorList;
        this.mDrawableList = mDrawableList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chart_indicator, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textIndicator.setText(mIndicatorList.get(position));
        holder.imgIndicator.setBackground(mDrawableList.get(position));
    }

    @Override
    public int getItemCount() {
        return mIndicatorList.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{

        View imgIndicator;
        TextView textIndicator;

        public ViewHolder(View itemView) {
            super(itemView);

            imgIndicator = itemView.findViewById(R.id.img_indicator);
            textIndicator = (TextView) itemView.findViewById(R.id.text_indicator);
        }
    }
}
