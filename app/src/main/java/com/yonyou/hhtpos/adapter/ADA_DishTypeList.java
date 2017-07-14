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
import com.yonyou.hhtpos.bean.DishTypeEntity;

/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：菜品列表适配器
 */
public class ADA_DishTypeList extends CommonAdapter<DishTypeEntity> {
    private boolean isCheck;
    private OnActionOrderDishListener mOnActionOrderDishListener;

    public ADA_DishTypeList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_dish_type;
    }

    @Override
    protected void convert(ViewHolder holder, DishTypeEntity dishTypeEntity, final int position) {

        final LinearLayout ll_item_root = holder.getView(R.id.ll_item_root);
        final TextView dish_name = holder.getView(R.id.dish_name);
        //设置选中效果
        RadioButton rbDishCheck = holder.getView(R.id.rb_dish_check);
        TextView specialSign = holder.getView(R.id.tv_special_sign);
        TextView recommendSign = holder.getView(R.id.tv_recommend_sign);
        ImageView sellOutSign = holder.getView(R.id.iv_sell_out_sign);
        if (dishTypeEntity.isCheck) {
            rbDishCheck.setVisibility(View.VISIBLE);
            rbDishCheck.setChecked(dishTypeEntity.isCheck);
        } else {
            rbDishCheck.setVisibility(View.GONE);
            rbDishCheck.setChecked(dishTypeEntity.isCheck);
        }
        //售罄状态item置灰效果
//        if () {
//            sellOutSign.setVisibility(View.VISIBLE);
//            specialSign.setVisibility(View.GONE);
//            recommendSign.setVisibility(View.GONE);
//            holder.setTextColor(R.id.dish_name, ContextCompat.getColor(mContext, R.color.color_999999));
//            holder.setTextColor(R.id.tv_dish_price, ContextCompat.getColor(mContext, R.color.color_999999));
//        } else {
//            holder.setTextColor(R.id.dish_name, ContextCompat.getColor(mContext, R.color.color_222222));
//            holder.setTextColor(R.id.tv_dish_price, ContextCompat.getColor(mContext, R.color.color_222222));
//        }
        ll_item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnActionOrderDishListener != null) {
                    mOnActionOrderDishListener.OnActionOrderDish(ll_item_root, position);
                }
            }
        });

    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public interface OnActionOrderDishListener {
        void OnActionOrderDish(View iv_start, int pos);
    }

    public void setOnActionOrderDishListener(OnActionOrderDishListener onActionOrderDishListener) {
        this.mOnActionOrderDishListener = onActionOrderDishListener;
    }
}
