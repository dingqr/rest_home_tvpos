package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.yonyou.framework.library.adapter.rv.CommonAdapter;
import com.yonyou.framework.library.adapter.rv.ViewHolder;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.ScreenUtil;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.CanteenTableEntity;

/**
 * Created by zj on 2017/7/8.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食桌台列表适配器
 */
public class ADA_CanteenList extends CommonAdapter<CanteenTableEntity> {
    public ADA_CanteenList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_table_list;
    }

    //    桌台状态：0 空闲 ，6 占用（消费中），7占用（部分支付），8 占用（锁定），9 占用（结清），5 预订，10 占用未消费  传（6,7,8,9,10）为查询占用，不传默认查询全部
    @Override
    protected void convert(ViewHolder holder, final CanteenTableEntity canteenTableEntity, int position) {
        //除了空闲状态，都显示操作时间
        //桌台名称
        holder.setText(R.id.tv_table_name, canteenTableEntity.tableName);
        //操作时间
        holder.setText(R.id.tv_make_time, String.valueOf(AppDateUtil.getTimeStamp(canteenTableEntity.openTime, AppDateUtil.HH_MM)));
        if (!TextUtils.isEmpty(canteenTableEntity.personNum)) {
            holder.setText(R.id.tv_person_num, canteenTableEntity.personNum);
        }
        holder.setText(R.id.tv_seat_num, "/" + canteenTableEntity.seatNum);

        //账单金额
        holder.setText(R.id.tv_money, mContext.getResources().getString(R.string.RMB_symbol) + canteenTableEntity.getBillMoney());
        //空闲状态：显示容纳人数，名称，空闲背景图片

        //预定：显示预定人数（暂时无此字段），可容纳人数、账单金额,预定人数（大）

        //占用（结清）：指餐台账单已经全部付清，显示：结清icon,餐台名称、结清时间、实收金额。
        switch (canteenTableEntity.tableStatus) {
            case 0:
                holder.setVisible(R.id.tv_make_time, false);
                holder.setVisible(R.id.tv_person_num, false);
                holder.setVisible(R.id.tv_seat_num, false);
                //桌台人数
                if (!TextUtils.isEmpty(canteenTableEntity.seatNum)) {
                    holder.setText(R.id.tv_money, canteenTableEntity.seatNum + mContext.getResources().getString(R.string.man));
                }
                //背景
                holder.setBackgroundRes(R.id.rl_table_root, R.drawable.bg_table_free);
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                //锁定小icon
                holder.setVisible(R.id.tv_locked, true);
                holder.setImageResource(R.id.tv_locked, R.drawable.ic_table_locked);
                holder.setBackgroundRes(R.id.rl_table_root, R.drawable.bg_table_unavailable);
                break;
            case 9:
                //结清
                holder.setVisible(R.id.tv_person_num, false);
                holder.setVisible(R.id.tv_seat_num, false);
                holder.setVisible(R.id.tv_locked, true);
                holder.setImageResource(R.id.tv_locked, R.drawable.ic_table_settled);
                holder.setText(R.id.tv_make_time, String.valueOf(AppDateUtil.getTimeStamp(canteenTableEntity.billTime, AppDateUtil.HH_MM)));
                holder.setText(R.id.tv_money, mContext.getString(R.string.RMB_symbol) + StringUtil.getString(canteenTableEntity.getRelReceiveAmount()));
                break;
            case 5:
                holder.setBackgroundRes(R.id.rl_table_root, R.drawable.bg_table_ordering);
                break;
        }
        //占用：显示账单金额、下单时间，占用icon标记
        //预定：显示可容纳人数，当前占用桌位个数

        // item点击事件
//        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}
