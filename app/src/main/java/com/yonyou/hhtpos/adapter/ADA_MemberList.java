package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.yonyou.framework.library.adapter.rv.CommonAdapter;
import com.yonyou.framework.library.adapter.rv.ViewHolder;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.MemberEntity;

/**
 * Created by zj on 2017/8/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：会员列表-适配器
 */
public class ADA_MemberList extends CommonAdapter<MemberEntity> {
    private int mSelectPos;

    public ADA_MemberList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_member_list;
    }

    @Override
    protected void convert(ViewHolder holder, MemberEntity memberEntity, int position) {
        View viewLeftLine = holder.getView(R.id.view_left_line);
        //设置item选中效果
        if (mSelectPos == position) {
            viewLeftLine.setVisibility(View.VISIBLE);
            holder.setBackgroundColor(R.id.ll_root, ContextCompat.getColor(mContext, R.color.color_eaeaea));
            holder.setBackgroundRes(R.id.iv_arrow_right,R.drawable.ic_red_arrow_right);
        } else {
            viewLeftLine.setVisibility(View.INVISIBLE);
            holder.setBackgroundColor(R.id.ll_root, ContextCompat.getColor(mContext, R.color.color_FFFFFF));
            holder.setBackgroundRes(R.id.iv_arrow_right,R.drawable.ic_grey_arrow_right);
        }
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
