package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.StoreEntity;

/**
 * Created by zj on 2017/6/27.
 * 邮箱：zjuan@yonyou.com
 * 描述：选择门店适配器
 */
public class ADA_ChooseStore extends CommonAdapterListView<StoreEntity> {

    private double mSelectedPos;

    public ADA_ChooseStore(Context context) {
        super(context);

    }


    @Override
    protected int itemLayoutId() {
        return R.layout.item_store;
    }

    @Override
    protected void convert(ViewHolderListView holder, StoreEntity storeEntity, int position) {
        holder.setText(R.id.tv_store_name, storeEntity.store_name);
        LinearLayout lltemILayout = holder.getView(R.id.ll_item_layout);
        if (mSelectedPos == position) {
            lltemILayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_fef2f0));
        } else {
            lltemILayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_f2f2f2));
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

}
