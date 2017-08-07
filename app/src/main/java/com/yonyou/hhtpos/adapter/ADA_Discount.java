package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.check.DiscountEntity;

import static com.yonyou.hhtpos.R.id.tv_discount_num;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class ADA_Discount extends CommonAdapterListView<DiscountEntity> {
    private Typeface customTypeface;

    public ADA_Discount(Context context) {
        super(context);
        customTypeface = Typeface.createFromAsset(context.getAssets(), "font/FangZhengSongHeiFanTi.ttf");
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_discount;
    }

    @Override
    protected void convert(ViewHolderListView holder, DiscountEntity bean, int position) {
        LinearLayout llRoot = holder.getView(R.id.item_discount_root);
        TextView tvDiscountNum = holder.getView(tv_discount_num);
        TextView tvDiscountUnit = holder.getView(R.id.tv_discount_unit);
        TextView tvRemark = holder.getView(R.id.tv_remark);
        //设置字体为方正宋黑繁体
        tvDiscountNum.setTypeface(customTypeface);
        tvDiscountUnit.setTypeface(customTypeface);
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.schemeName)) {
                holder.setText(R.id.tv_scheme_name, bean.schemeName);
            }
            if (!TextUtils.isEmpty(bean.remark)) {
                tvRemark.setVisibility(View.VISIBLE);
                tvRemark.setText(bean.remark);
            } else {
                tvRemark.setVisibility(View.INVISIBLE);
            }
            if (bean.totalDiscountRate != null) {
                tvDiscountNum.setText(StringUtil.getFormatDiscount(bean.totalDiscountRate));
            }
        }

        //置灰，所有文字设置成color_c7c7c7
//        holder.setBackgroundColor(R.id.item_discount_root, ContextCompat.getColor(mContext, R.color.color_c7c7c7));
    }
}
