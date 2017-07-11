package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;

import com.yonyou.framework.library.adapter.rv.CommonAdapter;
import com.yonyou.framework.library.adapter.rv.ViewHolder;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.DishTypeEntity;

/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：菜品列表适配器
 */
public class ADA_DishTypeList extends CommonAdapter<DishTypeEntity> {
    private int mSelectedPos;

    public ADA_DishTypeList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_dish_type;
    }

    @Override
    protected void convert(ViewHolder holder, DishTypeEntity dishTypeEntity, int position) {
        RadioButton rbDishCheck = holder.getView(R.id.rb_dish_check);
        //设置选中效果
        if (mSelectedPos == position) {
            rbDishCheck.setVisibility(View.VISIBLE);
            rbDishCheck.setChecked(true);
        } else {
            rbDishCheck.setVisibility(View.GONE);
        }
    }

    /**
     * 设置当前选中的item的位置
     *
     * @param position
     */
    public void setSelectItem(int position) {
        this.mSelectedPos = position;
    }
}
