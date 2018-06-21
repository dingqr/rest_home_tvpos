package com.smart.tvpos.adapter;

import android.content.Context;

import com.smart.framework.library.adapter.rv.CommonAdapter;
import com.smart.framework.library.adapter.rv.ViewHolder;
import com.smart.tvpos.R;

/**
 * Created by zj on 2017/8/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：会员列表-适配器
 */
public class ADA_MemberList extends CommonAdapter<String> {
    private int mSelectPos;

    public ADA_MemberList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.layout_recyclerview_swipe_item_default;
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {

    }

    /**
     * 设置当前选中的item的位置
     *
     * @param position
     */
    public void setSelectItem(int position) {
        this.mSelectPos = position;
    }
}
