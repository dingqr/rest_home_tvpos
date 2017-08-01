package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;

/**
 * Created by zj on 2017/8/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class ADA_ServiceCharge extends CommonAdapterListView<SettleAccountDataEntity.ServiceChargeDetail>{
    public ADA_ServiceCharge(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_service_charge;
    }

    @Override
    protected void convert(ViewHolderListView holder, SettleAccountDataEntity.ServiceChargeDetail bean, int position) {
        if(bean!=null) {
            holder.setText(R.id.tv_service_name,bean.getName());
            holder.setText(R.id.tv_service_charge,bean.getMoney());
        }
    }
}
