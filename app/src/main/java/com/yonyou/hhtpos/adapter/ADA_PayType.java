package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.PayTypeEntity;

/**
 * Created by zj on 2017/6/30.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class ADA_PayType extends CommonAdapterListView<PayTypeEntity> {

    private int mSelectedPos;

    public ADA_PayType(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_pay_type;
    }

    @Override
    protected void convert(ViewHolderListView holder, PayTypeEntity payTypeEntity, int position) {
        LinearLayout ll_pay_type = holder.getView(R.id.ll_pay_type);
        TextView tv_pay_type = holder.getView(R.id.tv_pay_type);
        holder.setText(R.id.tv_pay_type, payTypeEntity.payType);
        if (mSelectedPos == position) {
            tv_pay_type.setBackgroundResource(R.drawable.bg_side_light_red_4);
            tv_pay_type.setTextColor(ContextCompat.getColor(mContext, R.color.color_FFFFFF));
        } else {
            tv_pay_type.setBackgroundResource(R.drawable.bg_gray_side_gray_4);
            tv_pay_type.setTextColor(ContextCompat.getColor(mContext, R.color.color_272727));
        }
        //支付方式不支持时，按钮不可点击状态
        if (position == 2 || position == 3) {
            ll_pay_type.setOnClickListener(null);
            tv_pay_type.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
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
