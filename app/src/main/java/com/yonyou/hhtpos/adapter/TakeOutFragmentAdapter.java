package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.ui.dinner.wd.FRA_TakeOutLeft;
import com.yonyou.hhtpos.ui.dinner.wd.FRA_TakeOutList;

/**
 * 作者：liushuofei on 2017/7/4 16:55
 * 邮箱：lsf@yonyou.com
 */
public class TakeOutFragmentAdapter  extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 3;

    private Context mContext;
    private final String[] TITLES;

    public TakeOutFragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.mContext = context;
        TITLES = new String[]{ mContext.getString(R.string.take_out_status_all), mContext.getString(R.string.take_out_status_out_standing), mContext.getString(R.string.take_out_status_checked_out)};
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int id) {
        switch (id) {
            case FRA_TakeOutLeft.RB_ALL:
                return FRA_TakeOutList.newInstance(0);
            case FRA_TakeOutLeft.RB_OUT_STANDING:
                return FRA_TakeOutList.newInstance(1);
            case FRA_TakeOutLeft.RB_CHECKED_OUT:
                return FRA_TakeOutList.newInstance(2);
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


