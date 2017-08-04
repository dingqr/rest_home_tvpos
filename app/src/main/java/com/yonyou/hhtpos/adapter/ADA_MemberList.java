package com.yonyou.hhtpos.adapter;

import android.content.Context;

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
    public ADA_MemberList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_member_list;
    }

    @Override
    protected void convert(ViewHolder holder, MemberEntity memberEntity, int position) {

    }
}
