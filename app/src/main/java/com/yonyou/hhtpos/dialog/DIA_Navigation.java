package com.yonyou.hhtpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yonyou.framework.library.common.utils.ScreenUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_NavigationMain;
import com.yonyou.hhtpos.adapter.ADA_NavigationSecond;
import com.yonyou.hhtpos.base.DIA_Base;
import com.yonyou.hhtpos.bean.NavigationNewEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 左侧导航栏弹窗
 * 作者：liushuofei on 2017/6/27 14:43
 */
public class DIA_Navigation extends DIA_Base implements AdapterView.OnItemClickListener, ADA_NavigationMain.OnCheckChangedListener {

    @Bind(R.id.iv_back)
    ImageView mBackImg;
    @Bind(R.id.lv_navigation)
    ListView mListView;
    @Bind(R.id.ll_second_category)
    LinearLayout mSecondCategoryLayout;
    @Bind(R.id.lv_navigation_second)
    ListView mSecondListView;

    private ADA_NavigationMain mAdatper;
    private ADA_NavigationSecond mSecondAdapter;

    public DIA_Navigation(Context context, List<NavigationNewEntity> dataList) {
        super(context);

        // 一级菜单
        mAdatper = new ADA_NavigationMain(context, this);
        mListView.setAdapter(mAdatper);
        mListView.setOnItemClickListener(this);
        mAdatper.update(dataList);

        // 二级菜单
        mSecondAdapter = new ADA_NavigationSecond(context);
        mSecondListView.setAdapter(mSecondAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dia_navigation;
    }

    public Dialog getDialog() {
        mDialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog消失
        mDialog.getWindow().setWindowAnimations(R.style.style_left_in_anim);
        mDialog.getWindow().setGravity(Gravity.LEFT);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.0f; //背景灰度
        lp.width = 680; // 设置宽度
        lp.height = ScreenUtil.getScreenHeight((Activity) mContext); // 设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onChange(List<NavigationNewEntity.SecondList> dataList) {
        if (null == dataList || dataList.size() == 0) {
            mSecondCategoryLayout.setVisibility(View.GONE);
        } else {
            mSecondAdapter.update(dataList, true);
            mSecondCategoryLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_root})
    public void onClick() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }
}
