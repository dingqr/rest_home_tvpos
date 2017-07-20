package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.ui.dinner.ts.ACT_Canteen;
import com.yonyou.hhtpos.ui.dinner.ts.FRA_CanteenTableList;

/**
 * 作者：liushuofei on 2017/7/13 15:05
 * 邮箱：lsf@yonyou.com
 */
public class CanteenFragmentAdapter extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 5;

    private Context mContext;
    private final String[] TITLES;

    public CanteenFragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.mContext = context;
        TITLES = new String[]{ mContext.getString(R.string.free), mContext.getString(R.string.settle), mContext.getString(R.string.book), mContext.getString(R.string.occupy), mContext.getString(R.string.locked)};
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int id) {
        switch (id) {
            case ACT_Canteen.RB_FREE:
                return FRA_CanteenTableList.newInstance(0);
            case ACT_Canteen.RB_SETTLE:
                return FRA_CanteenTableList.newInstance(1);
            case ACT_Canteen.RB_BOOK:
                return FRA_CanteenTableList.newInstance(2);
            case ACT_Canteen.RB_OCCUPY:
                return FRA_CanteenTableList.newInstance(3);
            case ACT_Canteen.RB_LOCKED:
                return FRA_CanteenTableList.newInstance(4);
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
