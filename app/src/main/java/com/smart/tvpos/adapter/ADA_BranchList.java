package com.smart.tvpos.adapter;

import android.content.Context;

import com.smart.framework.library.adapter.lv.CommonAdapterListView;
import com.smart.framework.library.adapter.lv.ViewHolderListView;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.BranchAddressEntity;

/**
 * Created by JoJo on 2018/7/12.
 * wechat:18510829974
 * description:上海所有的养老院
 */

public class ADA_BranchList extends CommonAdapterListView<BranchAddressEntity> {
    public ADA_BranchList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_branch;
    }

    @Override
    protected void convert(ViewHolderListView holder, BranchAddressEntity bean, int position) {
        holder.setText(R.id.tv_branch_name, bean.getAreaName() + "." + bean.getName());
    }
}
