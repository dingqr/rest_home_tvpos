package com.smart.tvpos.adapter;

import android.content.Context;

import com.smart.framework.library.adapter.lv.CommonAdapterListView;
import com.smart.framework.library.adapter.lv.ViewHolderListView;
import com.smart.tvpos.R;

/**
 * Created by JoJo on 2018/7/2.
 * wechat:18510829974
 * description:
 */

public class ADA_CircleNurseProgress extends CommonAdapterListView<String> {
    public ADA_CircleNurseProgress(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_circle_nurse_progress;
    }

    @Override
    protected void convert(ViewHolderListView viewHolder, String item, int position) {

    }
}
