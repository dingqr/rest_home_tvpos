package com.smart.tvpos.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smart.framework.library.adapter.rv.MultiItemTypeAdapter;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.LatestDynamicEntity;
import com.smart.tvpos.bean.LatestWarnEntity;
import com.smart.tvpos.util.Constants;
import com.smart.tvpos.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ADA_LatestDynamic extends RecyclerView.Adapter<ADA_LatestDynamic.ViewHolder> {

    private List<LatestDynamicEntity> mList;
    Context mContext;
    MultiItemTypeAdapter.OnItemClickListener onItemClickListener;

    public ADA_LatestDynamic(Context context) {

        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder= new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_latest_dynamic, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//        //限定五条数据
//        if(position > 4){
//            return;
//        }

        if (Constants.TYPE_PAD_MODEL.equals(Build.MODEL)) {
            holder.textWhen.setTextSize(9);
            holder.textWhat.setTextSize(9);
        }

        if(position % 2 == 0){
            holder.llDynamicItem.setBackground(mContext.getResources().getDrawable(R.drawable.bg_view_14_corners));
        }

        LatestDynamicEntity entity = mList.get(position);
        SimpleDateFormat format =   new SimpleDateFormat("yyyy-MM-dd");
        Date date = entity.getPartyStart();

        holder.textWhen.setText("[" + format.format(date) + "]");
        holder.textWhat.setText(entity.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.itemView, holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateList(List<LatestDynamicEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(MultiItemTypeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llDynamicItem;
        TextView textWhen;
        TextView textWhat;

        public ViewHolder(View itemView) {
            super(itemView);
            llDynamicItem = (LinearLayout) itemView.findViewById(R.id.ll_dynamic_item);

            textWhen = (TextView) itemView.findViewById(R.id.dynamic_date);
            textWhat = (TextView) itemView.findViewById(R.id.dynamic_what);
        }
    }
}
