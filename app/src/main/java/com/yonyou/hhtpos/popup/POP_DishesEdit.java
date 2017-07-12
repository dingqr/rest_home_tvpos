package com.yonyou.hhtpos.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishesEdit;
import com.yonyou.hhtpos.bean.DishEditEntity;
import com.yonyou.hhtpos.widgets.BanSlideGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品编辑popup
 * 作者：liushuofei on 2017/7/12 14:38
 */
public class POP_DishesEdit extends PopupWindow {

    /**传入数据 */
    private Context context;

    /**布局 */
    private View convertView;
    private BanSlideGridView mGridView;

    private List<DishEditEntity> dataList;
    private ADA_DishesEdit mAdapter;

    public POP_DishesEdit(Context context) {
        super(context);
        this.context = context;

        setConvertView();
    }

    public void setConvertView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.pop_dishes_edit, null);
        mGridView = (BanSlideGridView)convertView.findViewById(R.id.gv_dishes_edit);
        mAdapter = new ADA_DishesEdit(context);
        mGridView.setAdapter(mAdapter);

        // 假数据
        dataList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            dataList.add(new DishEditEntity());
        }
        mAdapter.update(dataList);

        // 设置SelectPicPopupWindow的View
        this.setContentView(convertView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(393);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(376);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }
}
