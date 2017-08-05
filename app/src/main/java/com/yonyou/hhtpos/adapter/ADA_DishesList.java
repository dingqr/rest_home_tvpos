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
import com.yonyou.hhtpos.widgets.DashView;

/**
 * 点菜列表adapter
 * 作者：liushuofei on 2017/7/11 15:11
 */
public class ADA_DishesList extends BaseAbsAdapter<DishListEntity.Dishes> {

    /**套餐列表适配器 */
    private ADA_DishesPackage mPackageAdapter;

    public ADA_DishesList(Context context) {
        super(context);

        mPackageAdapter = new ADA_DishesPackage(context);
        for (int i = 0; i < 10; i++){
            mPackageAdapter.update("");
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
        holder.mListView.setAdapter(mPackageAdapter);
        return convertView;
    }

    /**
     * 填充数据
     */
    private void handleDataSource(DishListEntity.Dishes bean, ViewHolder holder, int pos){
        if (null == bean)
            return;

        // 下单时间和头布局
        if (pos == 0){
            if (!TextUtils.isEmpty(bean.getOrderTime())){
                holder.mDishesTime.setVisibility(View.VISIBLE);
                holder.mDishesTime.setText(AppDateUtil.getTimeStamp(Long.parseLong(bean.getOrderTime()), AppDateUtil.HH_MM) + "点菜");

                // 已下单菜品
                holder.mDishHeader.setVisibility(View.VISIBLE);
                holder.mDishHeader.setText(mContext.getString(R.string.ordered_dishes));
                holder.mDishHeader.setTextColor(mContext.getResources().getColor(R.color.color_eb6247));
            }else {
                holder.mDishesTime.setVisibility(View.GONE);

                // 未下单菜品
                holder.mDishHeader.setVisibility(View.VISIBLE);
                holder.mDishHeader.setText(mContext.getString(R.string.not_order_dishes));
                holder.mDishHeader.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            }
        }else {
            DishListEntity.Dishes preBean = mDataSource.get(pos - 1);
            if (null != preBean && !TextUtils.isEmpty(preBean.getOrderTime()) && !TextUtils.isEmpty(bean.getOrderTime()) && !preBean.getOrderTime().equals(bean.getOrderTime())){
                holder.mDishesTime.setVisibility(View.VISIBLE);
                holder.mDishesTime.setText(AppDateUtil.getTimeStamp(Long.parseLong(bean.getOrderTime()), AppDateUtil.HH_MM) + "点菜");

                holder.mDishHeader.setVisibility(View.GONE);
            }else if (null != preBean && TextUtils.isEmpty(preBean.getOrderTime()) && !TextUtils.isEmpty(bean.getOrderTime())){
                holder.mDishesTime.setVisibility(View.VISIBLE);
                holder.mDishesTime.setText(AppDateUtil.getTimeStamp(Long.parseLong(bean.getOrderTime()), AppDateUtil.HH_MM) + "点菜");

                // 已下单菜品
                holder.mDishHeader.setVisibility(View.VISIBLE);
                holder.mDishHeader.setText(mContext.getString(R.string.ordered_dishes));
                holder.mDishHeader.setTextColor(mContext.getResources().getColor(R.color.color_eb6247));
            } else {
                holder.mDishesTime.setVisibility(View.GONE);
                holder.mDishHeader.setVisibility(View.GONE);
            }
        }

        // 规格+菜品名称
        holder.mDishesName.setText(StringUtil.getString(bean.getDishName()));
        // 价格
        holder.mDishesPrice.setText(mContext.getString(R.string.RMB_symbol) + bean.getDishPrice());

        // 不是称重菜
        if (!TextUtils.isEmpty(bean.getIsWeighDish())){
            if (bean.getIsWeighDish().equals("N")){
                // 数量
                holder.mDishesCount.setText(mContext.getString(R.string.multiply) + (int)Double.parseDouble(bean.getQuantity()));
            }else if (bean.getIsWeighDish().equals("Y")){
                // 斤
                holder.mDishesCount.setText(bean.getQuantity() + mContext.getString(R.string.dish_weight_unit));
            }
        }

        // 备注
        holder.mDishesRemark.setText(getRemark(bean));

        // 等叫或即起
        setDishStatus(bean.getDishStatus(), holder);

        // 退增布局
        if (null != bean.getAbnormalList() && bean.getAbnormalList().size() > 0){
            holder.mHandleLv.setVisibility(View.VISIBLE);
            ADA_SpecialHandleDish mHandleAdapter = new ADA_SpecialHandleDish(mContext);
            holder.mHandleLv.setAdapter(mHandleAdapter);
            holder.mHandleLv.setPressed(false);
            holder.mHandleLv.setEnabled(false);
            mHandleAdapter.update(bean.getAbnormalList(), true);
        }else {
            holder.mHandleLv.setVisibility(View.GONE);
        }

        // 隐藏最后一根线
        if (pos == mDataSource.size() - 1){
            holder.mLineView.setVisibility(View.GONE);
        }else {
            holder.mLineView.setVisibility(View.VISIBLE);
        }
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
            stringBuffer.append(mContext.getString(R.string.hint_dish_cookery));
            stringBuffer.append(bean.getListShowPractice());
            stringBuffer.append("  ");
        }

        // 备注
        if (!TextUtils.isEmpty(bean.getListShowRemark())){
            stringBuffer.append(mContext.getString(R.string.hint_dish_remark));
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
                holder.mDishesStatus.setText("");
                break;
        }
    }

    static class ViewHolder {

        TextView mDishHeader;
        TextView mDishesTime;
        TextView mDishesName;
        TextView mDishesRemark;
        TextView mDishesPrice;
        TextView mDishesCount;
        TextView mDishesStatus;
        ImageView mVipLogo;
        BanSlideListView mListView;
        BanSlideListView mHandleLv;
        DashView mLineView;

        ViewHolder(View v) {
            mDishHeader = (TextView) v.findViewById(R.id.tv_dishes_status_header);
            mDishesTime = (TextView) v.findViewById(R.id.tv_dishes_time);
            mDishesName = (TextView) v.findViewById(R.id.tv_dishes_name);
            mDishesRemark = (TextView) v.findViewById(R.id.tv_dishes_remark);
            mDishesPrice = (TextView) v.findViewById(R.id.tv_dishes_price);
            mDishesCount = (TextView) v.findViewById(R.id.tv_dishes_count);
            mDishesStatus = (TextView) v.findViewById(R.id.tv_dishes_status);
            mVipLogo = (ImageView) v.findViewById(R.id.iv_vip_logo);
            mListView = (BanSlideListView) v.findViewById(R.id.lv_package);
            mLineView = (DashView) v.findViewById(R.id.dv_line);
            mHandleLv = (BanSlideListView) v.findViewById(R.id.lv_special_handle);
        }
    }
}
