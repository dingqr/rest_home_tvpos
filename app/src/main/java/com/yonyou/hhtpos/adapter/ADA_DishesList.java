package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.widgets.BanSlideListView;

/**
 * 点菜列表adapter
 * 作者：liushuofei on 2017/7/11 15:11
 */
public class ADA_DishesList extends BaseAbsAdapter<DishListEntity.Dishes> {

    /**套餐列表适配器 */
    private ADA_DishesPackage mAdapter;

    public ADA_DishesList(Context context) {
        super(context);

        mAdapter = new ADA_DishesPackage(context);
        for (int i = 0; i < 10; i++){
            mAdapter.update("");
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dishes_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DishListEntity.Dishes bean = mDataSource.get(position);
        handleDataSource(bean, holder, position);

        // 设置套餐数据
        holder.mListView.setAdapter(mAdapter);
        return convertView;
    }

    /**
     * 填充数据
     */
    private void handleDataSource(DishListEntity.Dishes bean, ViewHolder holder, int pos){
        if (null == bean)
            return;

        // 下单时间
        if (pos == 0){
            if (!TextUtils.isEmpty(bean.getOrderTime())){
                holder.mDishesTime.setVisibility(View.VISIBLE);
                holder.mDishesTime.setText(AppDateUtil.getTimeStamp(Long.parseLong(bean.getOrderTime()), AppDateUtil.HH_MM) + "点菜");
            }else {
                holder.mDishesTime.setVisibility(View.GONE);
            }
        }else {
            DishListEntity.Dishes preBean = mDataSource.get(pos - 1);
            if (null != preBean && !TextUtils.isEmpty(preBean.getOrderTime()) && !TextUtils.isEmpty(bean.getOrderTime()) && !preBean.getOrderTime().equals(bean.getOrderTime())){
                holder.mDishesTime.setVisibility(View.VISIBLE);
                holder.mDishesTime.setText(AppDateUtil.getTimeStamp(Long.parseLong(bean.getOrderTime()), AppDateUtil.HH_MM) + "点菜");
            }else {
                holder.mDishesTime.setVisibility(View.GONE);
            }
        }

        // 规格+菜品名称
        holder.mDishesName.setText(StringUtil.getString(bean.getDishName()));
        // 价格
        holder.mDishesPrice.setText(mContext.getString(R.string.RMB_symbol) + StringUtil.getString(bean.getDishPrice()));

        // 不是称重菜
        if (bean.getUnit() == 0){
            // 数量
            holder.mDishesCount.setText(mContext.getString(R.string.multiply) + bean.getQuantity());
        }else if (bean.getUnit() == 1){
            // 斤
            holder.mDishesCount.setText(bean.getQuantity() + mContext.getString(R.string.dish_weight_unit));
        }

        // 备注
        holder.mDishesRemark.setText(getRemark(bean));

        // 等叫或即起
        setDishStatus(bean.getDishStatus(), holder);
    }

    /**
     * 做法和备注，中间用空格隔开
     * @param bean
     * @return
     */
    private String getRemark(DishListEntity.Dishes bean){
        StringBuffer stringBuffer = new StringBuffer();

        // 做法
        if (!TextUtils.isEmpty(bean.getListShowPractice())){
            stringBuffer.append(bean.getListShowPractice());
        }

        // 备注
        if (!TextUtils.isEmpty(bean.getListShowRemark())){
            stringBuffer.append(" ");
            stringBuffer.append(bean.getListShowRemark());
        }
        return stringBuffer.toString();
    }

    /**
     * 设置菜品状态
     * @param status 等叫：7，即起：8，催菜：6，确认上菜：5
     * @param holder
     */
    private void setDishStatus(int status, ViewHolder holder){
        switch (status){
            case 7:
                holder.mDishesStatus.setText("等叫");
                break;

            case 8:
                // 即起不显示
                holder.mDishesStatus.setText("");
                break;

            default:
                break;
        }
    }

    static class ViewHolder {

        TextView mDishesTime;
        TextView mDishesName;
        TextView mDishesRemark;
        TextView mDishesPrice;
        TextView mDishesCount;
        TextView mDishesStatus;
        ImageView mVipLogo;
        BanSlideListView mListView;

        ViewHolder(View v) {
            mDishesTime = (TextView) v.findViewById(R.id.tv_dishes_time);
            mDishesName = (TextView) v.findViewById(R.id.tv_dishes_name);
            mDishesRemark = (TextView) v.findViewById(R.id.tv_dishes_remark);
            mDishesPrice = (TextView) v.findViewById(R.id.tv_dishes_price);
            mDishesCount = (TextView) v.findViewById(R.id.tv_dishes_count);
            mDishesStatus = (TextView) v.findViewById(R.id.tv_dishes_status);
            mVipLogo = (ImageView) v.findViewById(R.id.iv_vip_logo);
            mListView = (BanSlideListView) v.findViewById(R.id.lv_package);
        }
    }
}
