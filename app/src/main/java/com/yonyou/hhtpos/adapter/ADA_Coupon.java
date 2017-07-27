package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;

/**
 * Created by zj on 2017/7/27.
 * 邮箱：zjuan@yonyou.com
 * 描述：服务员结账-选择优惠券页面适配器
 */
public class ADA_Coupon extends CommonAdapterListView<CouponEntity> {
    private int mSelectedPos;

    public ADA_Coupon(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_coupon;
    }

    @Override
    protected void convert(ViewHolderListView holder, CouponEntity item, int position) {
        //设置选中效果
        if (mSelectedPos == position) {
            holder.setVisible(R.id.iv_check, true);
        } else {
            holder.setVisible(R.id.iv_check, false);
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
