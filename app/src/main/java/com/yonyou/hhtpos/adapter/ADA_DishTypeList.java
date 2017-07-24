package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.adapter.rv.CommonAdapter;
import com.yonyou.framework.library.adapter.rv.ViewHolder;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.dish.DishLabelEntity;
import com.yonyou.hhtpos.bean.dish.DishTastesEntity;
import com.yonyou.hhtpos.bean.dish.DishesEntity;

import java.util.List;

import static com.yonyou.hhtpos.R.id.tv_tastes;


/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：菜品列表适配器
 */
public class ADA_DishTypeList extends CommonAdapter<DishesEntity> {
    private boolean isCheck;
    private OnActionOrderDishListener mOnActionOrderDishListener;
    //字符串拼接
    private StringBuffer mBuffer;

    public ADA_DishTypeList(Context context) {
        super(context);
        mBuffer = new StringBuffer();
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_dish_type;
    }

    @Override
    protected void convert(ViewHolder holder, final DishesEntity dishesEntity, final int position) {

        final LinearLayout ll_item_root = holder.getView(R.id.ll_item_root);
        final TextView dishName = holder.getView(R.id.dish_name);
        final TextView tvTastes = holder.getView(tv_tastes);
        final ImageView ivWeightSign = holder.getView(R.id.iv_weight_sign);
        //回调item点击事件
        ll_item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnActionOrderDishListener != null) {
                    mOnActionOrderDishListener.OnActionOrderDish(ll_item_root, position, dishesEntity);
                }
            }
        });
        //设置item选中效果
        RadioButton rbDishCheck = holder.getView(R.id.rb_dish_check);
        TextView tvLabelOne = holder.getView(R.id.tv_label_one);
        TextView tvLabelTwo = holder.getView(R.id.tv_label_two);
        ImageView sellOutSign = holder.getView(R.id.iv_sell_out_sign);
        if (dishesEntity.isCheck) {
            rbDishCheck.setVisibility(View.VISIBLE);
            rbDishCheck.setChecked(dishesEntity.isCheck);
        } else {
            rbDishCheck.setVisibility(View.GONE);
            rbDishCheck.setChecked(dishesEntity.isCheck);
        }


        //设置接口返回数据
        if (dishesEntity != null) {
            //设置口味显示
            List<DishTastesEntity> tastes = dishesEntity.tastes;
            if (tastes.size() > 0) {
                tvTastes.setVisibility(View.VISIBLE);
                for (int i = 0; i < tastes.size(); i++) {
                    mBuffer.append(tastes.get(i).tasteName + " ");
                }
                tvTastes.setText(mBuffer.toString().trim());
            } else {
                tvTastes.setVisibility(View.INVISIBLE);
            }
            //设置价格
            holder.setText(R.id.tv_dish_price, "￥" + dishesEntity.getPrice() + "/" + mContext.getResources().getString(R.string.string_unit_quanlity));
            //设置是否是称重
            ivWeightSign.setVisibility(dishesEntity.isWeigh.equals("Y") ? View.VISIBLE : View.GONE);

            //售罄状态item置灰效果
//        if () {
//            sellOutSign.setVisibility(View.VISIBLE);
//            tvLabelOne.setVisibility(View.GONE);
//            tvLabelTwo.setVisibility(View.GONE);
//            holder.setTextColor(R.id.dish_name, ContextCompat.getColor(mContext, R.color.color_999999));
//            holder.setTextColor(R.id.tv_dish_price, ContextCompat.getColor(mContext, R.color.color_999999));
//        } else {
//            holder.setTextColor(R.id.dish_name, ContextCompat.getColor(mContext, R.color.color_222222));
//            holder.setTextColor(R.id.tv_dish_price, ContextCompat.getColor(mContext, R.color.color_222222));
//        }
            //设置显示的标签
            setLabels(dishesEntity, dishName, tvLabelOne, tvLabelTwo);
        }
    }

    /**
     * 设置显示的标签
     *
     * @param dishesEntity
     * @param dish_name
     * @param tvLabelOne
     * @param tvLabelTwo
     */
    private void setLabels(DishesEntity dishesEntity, TextView dish_name, TextView tvLabelOne, TextView tvLabelTwo) {
        //设置菜品标签,最多取前两个标签
        tvLabelOne.setVisibility(View.GONE);
        tvLabelTwo.setVisibility(View.GONE);
        List<DishLabelEntity> labels = dishesEntity.labels;
        dish_name.setText(dishesEntity.dishName);
        if (labels.size() > 0) {
            for (int i = 0; i < labels.size(); i++) {
                switch (i) {
                    case 0:
                        if (i > labels.size() - 1) {
                            return;
                        }
                        tvLabelOne.setVisibility(View.VISIBLE);
                        tvLabelOne.setText(labels.get(i).labelName);
                        continue;
                    case 1:
                        if (i > labels.size() - 1) {
                            return;
                        }
                        tvLabelTwo.setVisibility(View.VISIBLE);
                        tvLabelTwo.setText(labels.get(i).labelName);
                        return;
                }
            }
        } else {
            tvLabelOne.setVisibility(View.GONE);
            tvLabelTwo.setVisibility(View.GONE);
        }
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public interface OnActionOrderDishListener {
        void OnActionOrderDish(View iv_start, int pos, DishesEntity dishesEntity);
    }

    public void setOnActionOrderDishListener(OnActionOrderDishListener onActionOrderDishListener) {
        this.mOnActionOrderDishListener = onActionOrderDishListener;
    }
}
