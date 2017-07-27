package com.yonyou.hhtpos.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishesPlaceOrderEdit;
import com.yonyou.hhtpos.bean.DishEditEntity;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.global.DishConstants;
import com.yonyou.hhtpos.widgets.BanSlideGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 右侧菜品操作弹窗（已下单）
 * 作者：liushuofei on 2017/7/24 17:21
 */
public class POP_DishesPlaceOrderEdit extends PopupWindow implements ADA_DishesPlaceOrderEdit.OnSelectedListener {

    /**传入数据 */
    private Context context;
    private OnPlaceEditListener onEditListener;
    private DishListEntity.Dishes bean;

    /**布局 */
    private View convertView;
    private BanSlideGridView mGridView;

    private List<DishEditEntity> dataList;
    private ADA_DishesPlaceOrderEdit mAdapter;

    public POP_DishesPlaceOrderEdit(Context context, OnPlaceEditListener onEditListener, DishListEntity.Dishes bean) {
        super(context);
        this.context = context;
        this.onEditListener = onEditListener;
        this.bean = bean;

        setConvertView();
    }

    public void setConvertView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.pop_dishes_edit, null);
        mGridView = (BanSlideGridView)convertView.findViewById(R.id.gv_dishes_edit);
        mAdapter = new ADA_DishesPlaceOrderEdit(context, this, bean);
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
                onEditListener.updateDishStatus(DishConstants.STATUS_SERVING);
                break;

            case 1:
                onEditListener.specialHandleDish(DishConstants.DISH_WEIGHT);
                break;

            case 2:
                onEditListener.specialHandleDish(DishConstants.RETURN_DISH);
                break;

            case 3:
                onEditListener.specialHandleDish(DishConstants.SERVE_DISH);
                break;

            case 4:
                onEditListener.updateDishStatus(DishConstants.STATUS_REMINDER);
                break;

            case 5:
                onEditListener.specialHandleDish(DishConstants.DISH_TURN);
                break;

            default:
                break;
        }
    }

    public interface OnPlaceEditListener{
        void updateDishStatus(String status);

        void specialHandleDish(String specialHandle);
    }
}

