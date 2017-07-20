package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.widget.RadioButton;

import com.yonyou.framework.library.adapter.rv.CommonAdapter;
import com.yonyou.framework.library.adapter.rv.ViewHolder;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.CanteenTableEntity;

/**
 * Created by zj on 2017/7/10.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食-转入桌台选择列表适配器
 */
public class ADA_TableChooseList extends CommonAdapter<CanteenTableEntity> {
    private int mSelectedPos;

    public ADA_TableChooseList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_choose_table_list;
    }

    @Override
    protected void convert(ViewHolder holder, CanteenTableEntity canteenTableEntity, int position) {
        RadioButton rbTableCheck = holder.getView(R.id.rb_table_check);
        //设置选中效果
        if (mSelectedPos == position) {
            rbTableCheck.setChecked(true);
        } else {
            rbTableCheck.setChecked(false);
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
