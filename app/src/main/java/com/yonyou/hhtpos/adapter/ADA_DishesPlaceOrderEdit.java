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

/**
 * 作者：liushuofei on 2017/7/24 17:26
 * 邮箱：lsf@yonyou.com
 */
public class ADA_DishesPlaceOrderEdit extends BaseAbsAdapter<DishEditEntity> {

    private DishEditEntity currentBean;
    private int dishStatus;

    private ADA_DishesPlaceOrderEdit.OnSelectedListener onSelectedListener;

    /**催菜：6，等叫：7，叫起：8 */
    private static final int STATUS_REMINDER = 6;
    private static final int STATUS_WAIT_CALLED = 7;
    private static final int STATUS_SERVING = 8;

    public ADA_DishesPlaceOrderEdit(Context context, ADA_DishesPlaceOrderEdit.OnSelectedListener onSelectedListener, int dishStatus) {
        super(context);
        this.onSelectedListener = onSelectedListener;
        this.dishStatus = dishStatus;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ADA_DishesEdit.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dishes_edit, null);
            holder = new ADA_DishesEdit.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ADA_DishesEdit.ViewHolder) convertView.getTag();
        }

        final DishEditEntity bean = mDataSource.get(position);
        handleDataSource(position, holder, bean);
        return convertView;
    }

    private void handleDataSource(final int pos, ADA_DishesEdit.ViewHolder holder, final DishEditEntity bean){
        // 设置选中状态
        if (bean.isCheck()){
            holder.mOperationTxt.setChecked(true);
        }else {
            holder.mOperationTxt.setChecked(false);
        }

        holder.mOperationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bean.equals(currentBean)){
                    bean.setCheck(true);
                    if (null != currentBean){
                        currentBean.setCheck(false);
                    }
                    currentBean = bean;

                    notifyDataSetChanged();
                }
                onSelectedListener.onSelected(pos);
            }
        });

        switch (pos){
            case 0:
                holder.mOperationTxt.setText("叫起");
                break;

            case 1:
                holder.mOperationTxt.setText("称重确认");
                break;

            case 2:
                holder.mOperationTxt.setText("退菜");
                break;

            case 3:
                holder.mOperationTxt.setText("赠送");
                break;

            case 4:
                holder.mOperationTxt.setText("催菜");
                break;

            case 5:
                holder.mOperationTxt.setText("转台");
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

