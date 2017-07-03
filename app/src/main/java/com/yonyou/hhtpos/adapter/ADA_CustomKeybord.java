package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;

/**
 * Created by zj on 2017/6/30.
 * 邮箱：zjuan@yonyou.com
 * 描述：自定义键盘的适配器
 */
public class ADA_CustomKeybord extends CommonAdapterListView<String> {

    public ADA_CustomKeybord(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_number_keybord;
    }

    @Override
    protected void convert(ViewHolderListView holder, String item, int position) {
        //清除按钮
        if (position == getCount() - 1) {
            holder.setBackgroundRes(R.id.tv_number, R.drawable.ic_number_clear);
            return;
        }
        //“.”
        if (position == getCount() - 3) {
            holder.setBackgroundRes(R.id.tv_number, R.drawable.ic_number_point);
            return;
        }
        holder.setText(R.id.tv_number, item);
    }
}
