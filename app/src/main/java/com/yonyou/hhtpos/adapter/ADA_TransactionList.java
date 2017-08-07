package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.rv.CommonAdapter;
import com.yonyou.framework.library.adapter.rv.ViewHolder;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.TransactionRecordEntity;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：交易记录Adapter
 */
public class ADA_TransactionList extends CommonAdapter<TransactionRecordEntity> {
    public ADA_TransactionList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_transaction_record;
    }

    @Override
    protected void convert(ViewHolder holder, TransactionRecordEntity transactionRecordEntity, int position) {

    }
}
