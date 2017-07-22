package com.yonyou.hhtpos.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.dialog.DIA_ScanCodeNew;
import com.yonyou.hhtpos.ui.scan.FRA_CustomerScan;
import com.yonyou.hhtpos.ui.scan.FRA_ScanCustomer;

/**
 * 作者：liushuofei on 2017/7/21 19:28
 * 邮箱：lsf@yonyou.com
 */
public class ScanCodeFragmentAdapter  extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 2;

    private DIA_ScanCodeNew mContext;
    private final String[] TITLES;

    public ScanCodeFragmentAdapter(FragmentManager fragmentManager, DIA_ScanCodeNew context) {
        super(fragmentManager);
        this.mContext = context;
        TITLES = new String[]{ mContext.getString(R.string.scan_customer_code), mContext.getString(R.string.customer_scan_code)};
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int id) {
        switch (id) {
            case DIA_ScanCodeNew.SCAN_CUSTOMER:
                return new FRA_ScanCustomer();
            case DIA_ScanCodeNew.CUSTOMER_SCAN:
                return new FRA_CustomerScan();
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
