package com.yonyou.hhtpos.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
public class POP_DishesEdit extends PopupWindow implements ADA_DishesEdit.OnSelectedListener {

    /**传入数据 */
    private Context context;
    private OnEditListener onEditListener;

    /**布局 */
    private View convertView;
    private BanSlideGridView mGridView;

    private List<DishEditEntity> dataList;
    private ADA_DishesEdit mAdapter;

    /**催菜：6，等叫：7，叫起：8 */
    private static final String STATUS_REMINDER = "6";
    private static final String STATUS_WAIT_CALLED = "7";
    private static final String STATUS_SERVING = "8";

    public POP_DishesEdit(Context context, OnEditListener onEditListener) {
        super(context);
        this.context = context;
        this.onEditListener = onEditListener;

        setConvertView();
    }

    public void setConvertView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.pop_dishes_edit, null);
        mGridView = (BanSlideGridView)convertView.findViewById(R.id.gv_dishes_edit);
        mAdapter = new ADA_DishesEdit(context, this);
        mGridView.setAdapter(mAdapter);

        // 数据
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

    @Override
    public void onSelected(int pos) {
        switch (pos){
            case 0:
                onEditListener.updateCount(false);
                break;

            case 1:
                onEditListener.updateCount(true);
                break;

            case 2:
                onEditListener.updateDish();
                break;

            case 3:
                onEditListener.deleteDish();
                break;

            case 4:
                onEditListener.updateDishStatus(STATUS_WAIT_CALLED);
                break;

            case 5:
                onEditListener.updateDishStatus(STATUS_SERVING);
                break;

            default:
                break;
        }
    }

    public interface OnEditListener{
        void updateCount(boolean isAdd);

        void updateDish();

        void updateDishStatus(String status);

        void deleteDish();
    }
}
