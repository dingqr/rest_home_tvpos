package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishDate;
import com.yonyou.hhtpos.base.DIA_Base;
import com.yonyou.hhtpos.widgets.BanSlideGridView;

import butterknife.Bind;

/**
 * 菜品估清/时价弹窗
 * 作者：liushuofei on 2017/8/4 17:44
 */
public class DIA_SetDishStatus extends DIA_Base {

    @Bind(R.id.gv_dish_date)
    BanSlideGridView mGridView;

    private ADA_DishDate mAdapter;

    public DIA_SetDishStatus(Context context) {
        super(context);

        mAdapter = new ADA_DishDate(context);
        mGridView.setAdapter(mAdapter);
        for (int i = 0; i < 4; i++){
            mAdapter.update("");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dia_set_dish_status;
    }

    public Dialog getDialog() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // lp.dimAmount = 0.0f; 背景灰度
        lp.width = 594; // 设置宽度
        lp.height = 760; // 设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }
}
