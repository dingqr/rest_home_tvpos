package com.smart.tvpos.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smart.tvpos.R;
import com.smart.tvpos.bean.LatestWarnEntity;
import com.smart.tvpos.util.Constants;

import java.util.List;

public class ADA_LatestWarn extends RecyclerView.Adapter<ADA_LatestWarn.ViewHolder> {

    private List<LatestWarnEntity> mList;
    Context mContext;

    public ADA_LatestWarn(Context context) {

        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder= new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_latest_warn, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //限定30条数据
        if(position > 29){
            return;
        }

        if (Constants.TYPE_PAD_MODEL.equals(Build.MODEL)) {
            holder.textTitle.setTextSize(9);
            holder.textWho.setTextSize(9);
            holder.textWhere.setTextSize(9);
            holder.textWhat.setTextSize(9);
        }

        if(position % 2 == 0){
            holder.llWarnItem.setBackground(mContext.getResources().getDrawable(R.drawable.bg_view_14_corners));
        }

        LatestWarnEntity entity = mList.get(position);

        if(entity.getState().equals(mContext.getResources().getString(R.string.string_solved)) ) {
            //已解决
            holder.textTitle.setText(mContext.getResources().getString(R.string.string_solved));
            holder.textTitle.setBackground(mContext.getResources().getDrawable(R.drawable.bg_view_1_corners));
        }
        else {
            holder.textTitle.setText(mContext.getResources().getString(R.string.string_new_alert));
            holder.textTitle.setBackground(mContext.getResources().getDrawable(R.drawable.bg_view_5_corners));

        }

        holder.textWho.setText(entity.getUserName());
        holder.textWhere.setText(entity.getBuildingName() + entity.getFloorName());
        holder.textWhat.setText(entity.getTypeChild());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateList(List<LatestWarnEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llWarnItem;
        TextView textTitle;
        TextView textWho;
        TextView textWhere;
        TextView textWhat;

        public ViewHolder(View itemView) {
            super(itemView);
            llWarnItem = (LinearLayout) itemView.findViewById(R.id.ll_warn_item);

            textTitle = (TextView) itemView.findViewById(R.id.warn_title);
            textWho = (TextView) itemView.findViewById(R.id.warn_who);
            textWhere = (TextView) itemView.findViewById(R.id.warn_where);
            textWhat = (TextView) itemView.findViewById(R.id.warn_what);
        }
    }
}
