package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.ui.book.FRA_BookLeft;
import com.yonyou.hhtpos.ui.book.FRA_BookList;

/**
 * 预定总览左侧viewpager adapter
 * 作者：liushuofei on 2017/7/1 16:34
 */
public class PreviewFragmentAdapter extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 4;

    private  Context mContext;
    private final String[] TITLES;

    public PreviewFragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.mContext = context;
        TITLES = new String[]{ mContext.getString(R.string.order_status_all), mContext.getString(R.string.order_status_accept), mContext.getString(R.string.order_status_arrive), mContext.getString(R.string.order_status_cancel) };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int id) {
        switch (id) {
            case FRA_BookLeft.RB_ALL:
                return FRA_BookList.newInstance(0);
            case FRA_BookLeft.RB_ACCEPT:
                return FRA_BookList.newInstance(1);
            case FRA_BookLeft.RB_ARRIVE:
                return FRA_BookList.newInstance(2);
            case FRA_BookLeft.RB_CANCEL:
                return FRA_BookList.newInstance(3);
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}

