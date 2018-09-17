package com.smart.tvpos.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.smart.framework.library.adapter.lv.CommonAdapterListView;
import com.smart.framework.library.adapter.lv.ViewHolderListView;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.JobItemEntity;

/**
 * Created by JoJo on 2018/7/2.
 * wechat:18510829974
 * description:
 */

public class ADA_NurseProgressItem extends CommonAdapterListView<JobItemEntity> {

    int[] drawableSet = new int[]{R.drawable.bg_view_1_corners,R.drawable.bg_view_2_corners, R.drawable.bg_view_3_corners,
            R.drawable.bg_view_4_corners,R.drawable.bg_view_5_corners, R.drawable.bg_view_6_corners,
            R.drawable.bg_view_7_corners,R.drawable.bg_view_8_corners, R.drawable.bg_view_9_corners,
            R.drawable.bg_view_10_corners,R.drawable.bg_view_11_corners, R.drawable.bg_view_12_corners,
            R.drawable.bg_view_13_corners};

    public ADA_NurseProgressItem(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_bar_nurse_progress;
    }

    @Override
    protected void convert(ViewHolderListView holder, JobItemEntity bean, int position) {

        View completePercent = holder.getView(R.id.barPercentChatView);
        View allPercent = holder.getView(R.id.barChatView);
        if (bean != null) {
            holder.setText(R.id.tv_item_name, bean.getItemName());

            FrameLayout.LayoutParams paramsD = (FrameLayout.LayoutParams) completePercent.getLayoutParams();
            FrameLayout.LayoutParams paramsA = (FrameLayout.LayoutParams) allPercent.getLayoutParams();

            float percent = bean.getNumD() / bean.getNumA();

            paramsD.width = (int) (paramsA.width * percent);

            completePercent.setLayoutParams(paramsD);
            completePercent.setBackgroundResource(drawableSet[position % drawableSet.length]);

            holder.setText(R.id.tv_item_percent, String.valueOf(Math.round(percent * 100)));
        }
    }
}
