package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 去掉滑动效果的viewpager
 * 作者：liushuofei on 2017/4/26 17:37
 */
public class NoAnimationViewPager extends ViewPager {


    public NoAnimationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoAnimationViewPager(Context context) {
        super(context);
    }

    //去除页面切换时的滑动翻页效果  
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

}  