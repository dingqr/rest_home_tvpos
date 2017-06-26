package com.yonyou.hhtpos.widgets;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.yonyou.hhtpos.R;

/**
 * Created by ybing on 2017/6/24.
 */

public class DrawerView extends LinearLayout{
    /**上下文*/
    private Context mContext;
    /**是否包含左、右抽屉*/
    private boolean isContainsLeft = false;
    private boolean isContainsRight = true;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerbar;
    private RelativeLayout rv_left_layout,rv_right_layout;

    public DrawerView(Context context) {
        this(context,null);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
        initEvent();
    }

    private void initEvent() {
        Activity activity = (Activity) mContext;
        drawerbar = new ActionBarDrawerToggle(activity, drawerLayout, null, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerbar);
    }

    private void initView() {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_view,this);
        drawerLayout = (DrawerLayout) convertView.findViewById(R.id.main_drawer_layout);
        drawerLayout.setScrimColor(0x00000000);
        if (isContainsLeft){
            rv_left_layout = (RelativeLayout) convertView.findViewById(R.id.main_left_drawer_layout);
            View left = LayoutInflater.from(getContext()).inflate(R.layout.drawer_item_layout, null);
            rv_left_layout.addView(left);
        }
        if (isContainsRight){
            rv_right_layout = (RelativeLayout) convertView.findViewById(R.id.main_right_drawer_layout);
            View right = LayoutInflater.from(getContext()).inflate(R.layout.drawer_item_layout, null);
            rv_right_layout.addView(right);
        }
    }
}
