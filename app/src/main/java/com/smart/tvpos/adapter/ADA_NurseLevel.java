package com.smart.tvpos.adapter;

import android.content.Context;

import com.smart.framework.library.adapter.lv.CommonAdapterListView;
import com.smart.framework.library.adapter.lv.ViewHolderListView;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.NurseLevelEntity;

/**
 * Created by JoJo on 2018/7/25.
 * wechat:18510829974
 * description:柱状图护理级别adapter
 */

public class ADA_NurseLevel extends CommonAdapterListView<NurseLevelEntity> {
    int[] drawableLeft = {R.drawable.ic_special_level, R.drawable.ic_one_level, R.drawable.ic_two_level, R.drawable.ic_three_level, R.drawable.ic_60_69, R.drawable.ic_60_down, R.drawable.ic_70_79};

    public ADA_NurseLevel(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_nurse_level;
    }

    @Override
    protected void convert(ViewHolderListView holder, NurseLevelEntity bean, int position) {
        holder.setText(R.id.tv_level, bean.getName());
        holder.setImageResource(R.id.iv_level, drawableLeft[position % drawableLeft.length]);
    }
}
