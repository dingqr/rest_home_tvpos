package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.yonyou.framework.library.adapter.rv.CommonAdapter;
import com.yonyou.framework.library.adapter.rv.ViewHolder;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.MealAreaEntity;

/**
 * Created by zj on 2017/7/10.
 * 邮箱：zjuan@yonyou.com
 * 描述：餐区选择的适配器
 */
public class ADA_TableArea extends CommonAdapter<MealAreaEntity> {
    private int mSelectedPos;
//    private onItemClickListener mListener;

    public ADA_TableArea(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_table_area_choose;
    }

    @Override
    protected void convert(ViewHolder holder, MealAreaEntity mealAreaEntity, final int position) {
//        View itemView = holder.getView(R.id.layout_item);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) {
//                    mListener.onItemClick(position);
//                }
//            }
//        });
        holder.setText(R.id.tv_table_area_name,mealAreaEntity.getDiningAreaName());
        TextView tvTableAreaAame = holder.getView(R.id.tv_table_area_name);
        //设置选中效果
        if (mSelectedPos == position) {
            tvTableAreaAame.setBackgroundResource(R.drawable.bg_side_light_red_4);
            tvTableAreaAame.setTextColor(ContextCompat.getColor(mContext, R.color.color_FFFFFF));
        } else {
            tvTableAreaAame.setBackgroundResource(R.drawable.bg_gray_side_gray_4);
            tvTableAreaAame.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
        }


    }

    /**
     * 设置当前选中的item的位置
     *
     * @param position
     */
    public void setSelectItem(int position) {
        this.mSelectedPos = position;
    }
//
//    public interface onItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public void setOnItemClickListener(onItemClickListener mListener) {
//        this.mListener = mListener;
//    }
}
