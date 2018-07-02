package com.smart.tvpos.adapter;

import android.content.Context;

import com.smart.framework.library.adapter.lv.CommonAdapterListView;
import com.smart.framework.library.adapter.lv.ViewHolderListView;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.JobItemEntity;
import com.smart.tvpos.widgets.PieMarkChatView;

/**
 * Created by JoJo on 2018/7/2.
 * wechat:18510829974
 * description:
 */

public class ADA_CircleNurseProgress extends CommonAdapterListView<JobItemEntity> {
    public ADA_CircleNurseProgress(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_circle_nurse_progress;
    }

    @Override
    protected void convert(ViewHolderListView holder, JobItemEntity bean, int position) {
        PieMarkChatView pieMarkChatView = holder.getView(R.id.pieMarkChatView);
        if (bean != null) {
            holder.setImageResource(R.id.iv_bg, position % 2 == 0 ? R.drawable.bg_nurse_progress_style_one : R.drawable.bg_nurse_progress_style_two);
            holder.setText(R.id.tv_item_name, bean.getItemName());
            holder.setText(R.id.tv_item_title, "护理任务" + (position + 1));
            pieMarkChatView.setCurrentProgress((int) (bean.getNumD() * 1f * 100 / bean.getNumA()));
        }
    }
}
