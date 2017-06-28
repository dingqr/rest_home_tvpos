package com.yonyou.hhtpos.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_NavigationSecond;

import butterknife.Bind;

/**
 * 左侧二级导航栏popup
 * 作者：liushuofei on 2017/6/27 17:11
 */
public class POP_NavigationSecond extends PopupWindow implements AdapterView.OnItemClickListener{

    ListView mListView;

    private ADA_NavigationSecond mAdapter;

    /**传入数据 */
    private Context context;
    private OnItemClick onItemClick;

    /**布局 */
    private View convertView;

    public POP_NavigationSecond(Context context) {
        super(context);
        this.context = context;

        setConvertView();
    }

    public POP_NavigationSecond(Context context, OnItemClick onItemClick) {
        super(context);
        this.context = context;
        this.onItemClick = onItemClick;

        setConvertView();
    }

    public void setConvertView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.pop_navigation_second, null);
        mListView = (ListView) convertView.findViewById(R.id.lv_navigation_second);
        mListView.setOnItemClickListener(this);

        mAdapter = new ADA_NavigationSecond(context);
        mListView.setAdapter(mAdapter);

        // 测试数据
        for (int i = 0; i < 3; i++){
            mAdapter.update("");
        }

        // 设置SelectPicPopupWindow的View
        this.setContentView(convertView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    public interface OnItemClick {
        void onClick();
    }

}

