package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.NavigationEntity;

import static com.yonyou.hhtpos.R.id.useLogo;


/**
 * Created by zj on 2017/6/23.
 * 邮箱：zjuan@yonyou.com
 * 描述：左侧导航栏：二次封装二级列表
 */
public class LeftNavigationView extends LinearLayout {
    private Context mContext;
    private LeftExpandableView expandableView;
    private ImageView ivUserLogo;
    private TextView tvMessageCount;
    private TextView tvBusinessTime;
    private TextView tvShopName;

    public LeftNavigationView(Context context) {
        this(context, null);
    }

    public LeftNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        // 设置方向
        this.setOrientation(LinearLayout.VERTICAL);

        View view = LayoutInflater.from(mContext).inflate(R.layout.left_nevigation_linear, this);
        expandableView = (LeftExpandableView) view.findViewById(R.id.expandableview);
        ivUserLogo = (ImageView) view.findViewById(R.id.iv_user_logo);
        tvMessageCount = (TextView) view.findViewById(R.id.tv_message_count);
        tvBusinessTime = (TextView) view.findViewById(R.id.tv_business_time);
        tvShopName = (TextView) view.findViewById(R.id.tv_shop_name);
    }

    /**
     * 传入数据
     *
     * @param bean
     */
    public void setData(NavigationEntity bean) {
        if (bean != null) {
            expandableView.setData(bean.groupData, bean.childDta);
            if (TextUtils.isEmpty(bean.user_logo)) {
                Glide.with(mContext).load(useLogo).into(ivUserLogo);
            }
            if (TextUtils.isEmpty(bean.message_count)) {
                tvMessageCount.setText(bean.message_count);
            }
            if (TextUtils.isEmpty(bean.business_time)) {
                tvBusinessTime.setText(bean.business_time);
            }
            if (TextUtils.isEmpty(bean.shop_name)) {
                tvShopName.setText(bean.shop_name);
            }
        }

    }
}
