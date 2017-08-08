package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RadioButton;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.DishEditEntity;
import com.yonyou.hhtpos.global.DishConstants;

/**
 * 菜品编辑adapter
 * 作者：liushuofei on 2017/7/12 15:15
 */
public class ADA_DishesEdit extends BaseAbsAdapter<DishEditEntity> {

    private DishEditEntity currentBean;
    private String dishStatus;

    private OnSelectedListener onSelectedListener;

    public ADA_DishesEdit(Context context, OnSelectedListener onSelectedListener, String dishStatus) {
        super(context);
        this.onSelectedListener = onSelectedListener;
        this.dishStatus = dishStatus;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dishes_edit, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DishEditEntity bean = mDataSource.get(position);
        handleDataSource(position, holder, bean);
        return convertView;
    }

    private void handleDataSource(final int pos, ViewHolder holder, final DishEditEntity bean){
        // 设置选中状态
        if (bean.isCheck()){
            holder.mOperationTxt.setChecked(true);
        }else {
            holder.mOperationTxt.setChecked(false);
        }

        // 等叫和即起状态
        if ((dishStatus.equals(DishConstants.STATUS_WAIT_CALLED) && pos == 4) || (dishStatus.equals(DishConstants.STATUS_SERVING) && pos == 5)){
            holder.mOperationTxt.setBackground(mContext.getResources().getDrawable(R.drawable.bg_dishes_edit_selected_no));
            holder.mOperationTxt.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            holder.mOperationTxt.setEnabled(false);
        }else {
            holder.mOperationTxt.setTextColor(mContext.getResources().getColor(R.color.color_222222));
            holder.mOperationTxt.setEnabled(true);
        }

        holder.mOperationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bean.equals(currentBean)){
                    // pos:0 1 2 3
                    bean.setCheck(true);
                    if (null != currentBean){
                        currentBean.setCheck(false);
                    }
                    currentBean = bean;

                    // pos:4 5（等叫和即起）
                    if (pos == 4){
                        dishStatus = DishConstants.STATUS_WAIT_CALLED;
                    }else if (pos == 5){
                        dishStatus = DishConstants.STATUS_SERVING;
                    }
                    notifyDataSetChanged();
                }
                onSelectedListener.onSelected(pos);
            }
        });

        switch (pos){
            case 0:
                holder.mOperationTxt.setText("数量-1");
                break;

            case 1:
                holder.mOperationTxt.setText("数量+1");
                break;

            case 2:
                holder.mOperationTxt.setText("修改菜品");
                break;

            case 3:
                holder.mOperationTxt.setText("删除菜品");
                break;

            case 4:
                holder.mOperationTxt.setText("等叫");
                break;

            case 5:
                holder.mOperationTxt.setText("即起");
                break;

            default:
                break;
        }
    }

    static class ViewHolder {
        RadioButton mOperationTxt;

        ViewHolder(View v) {
            mOperationTxt = (RadioButton) v.findViewById(R.id.rb_operation);
        }
    }

    public interface OnSelectedListener{
        void onSelected(int pos);
    }
}
