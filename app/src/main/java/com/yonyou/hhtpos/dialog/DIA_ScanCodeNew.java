package com.yonyou.hhtpos.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ScanCodeFragmentAdapter;
import com.yonyou.hhtpos.widgets.PagerSlidingTabStrip;

/**
 * 扫顾客码/顾客扫码弹窗
 * 作者：liushuofei on 2017/7/22 09:52
 */
public class DIA_ScanCodeNew extends DialogFragment implements View.OnClickListener{

    /**界面控件 */
    PagerSlidingTabStrip mTab;
    ViewPager mViewPager;
    ImageView mCloseImg;

    private ScanCodeFragmentAdapter mFragmentAdapter;

    /**记录前一个被选中的tab的位置 */
    private int prePosition;

    public static final int SCAN_CUSTOMER = 0;
    public static final int CUSTOMER_SCAN = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 去出标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 点击外部不消失
        getDialog().setCanceledOnTouchOutside(false);
        // 点击返回键不消失，需要监听OnKeyListener:
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        // 设置布局
        View convertView = inflater.inflate(R.layout.dia_scan_code, container);
        mTab = (PagerSlidingTabStrip) convertView.findViewById(R.id.psts_tab);
        mViewPager = (ViewPager) convertView.findViewById(R.id.vp_scan_code);
        mCloseImg = (ImageView) convertView.findViewById(R.id.iv_close);
        mCloseImg.setOnClickListener(this);

        setVpAdapter();

        initSlidingTab();

        return convertView;
    }

    @Override
    public void onStart() { //在onStart()中设置起作用，在onCreateView不行
        super.onStart();

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER);//放置位置
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //对话框背景
        window.setLayout(1000,905); //宽高
        window.setDimAmount(0.8f);// 灰度
    }

    private void setVpAdapter() {
        mFragmentAdapter = new ScanCodeFragmentAdapter(getChildFragmentManager(), this);
        mViewPager.setAdapter(mFragmentAdapter);
    }

    private void initSlidingTab(){
        mTab.setViewPager(mViewPager);
        mTab.setIndicatorColor(getResources().getColor(R.color.color_eb6247));
        TextView tabTextView = (TextView) mTab.getTabsContainer().getChildAt(prePosition); //设置默认选中第一个时为红色
        tabTextView.setTextColor(getResources().getColor(R.color.color_eb6247));

        mTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TextView tabTextView = (TextView) mTab.getTabsContainer().getChildAt(position);
                tabTextView.setTextColor(getResources().getColor(R.color.color_eb6247));
                TextView preTabTextView = (TextView) mTab.getTabsContainer().getChildAt(prePosition);
                preTabTextView.setTextColor(getResources().getColor(R.color.color_222222));
                prePosition = position;
                //获取当前显示的Fragment
                //mCurrentFramgent = (FRA_CanteenTableList) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                dismiss();
                break;

            default:
                break;
        }
    }

}
