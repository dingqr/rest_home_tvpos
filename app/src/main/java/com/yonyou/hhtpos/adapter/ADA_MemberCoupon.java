package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：会员-优惠券列表
 */
public class ADA_MemberCoupon extends CommonAdapterListView<CouponEntity> {
    private int mSelectedPos;

    public ADA_MemberCoupon(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_member_coupon_list;
    }

    @Override
    protected void convert(ViewHolderListView holder, CouponEntity item, int position) {
        //设置item的显示间距
        RelativeLayout lRoot = holder.getView(R.id.ll_root);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (position == 0) {
            params.setMargins(28, 30, 28, 0);
        } else {
            params.setMargins(28, 0, 28, 0);
        }
        lRoot.setLayoutParams(params);
    }
}
