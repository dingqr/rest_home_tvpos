package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.RadioButton;

import com.yonyou.framework.library.adapter.rv.CommonAdapter;
import com.yonyou.framework.library.adapter.rv.ViewHolder;
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.CanteenTableEntity;

/**
 * Created by zj on 2017/7/10.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食-转入桌台选择列表适配器
 */
public class ADA_TableChooseList extends CommonAdapter<CanteenTableEntity> {
    private int mSelectedPos = -1;

    public ADA_TableChooseList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_choose_table_list;
    }

    @Override
    protected void convert(ViewHolder holder, CanteenTableEntity canteenTableEntity, int position) {
        int mActualPos = position - 1;
        Elog.e("canteenTableEntity=" + canteenTableEntity.tableName + "Pos=" + mActualPos);
        RadioButton rbTableCheck = holder.getView(R.id.rb_table_check);
//        //除了空闲状态，都显示操作时间
//        //桌台名称
        holder.setText(R.id.tv_table_name, canteenTableEntity.tableName);
//        //操作时间
        holder.setText(R.id.tv_make_time, String.valueOf(AppDateUtil.getTimeStamp(canteenTableEntity.openTime, AppDateUtil.HH_MM)));
        if (!TextUtils.isEmpty(canteenTableEntity.personNum)) {
            holder.setText(R.id.tv_person_num, canteenTableEntity.personNum);
        }
        holder.setText(R.id.tv_seat_num, "/" + canteenTableEntity.seatNum);

        //账单金额
        holder.setText(R.id.tv_money, mContext.getResources().getString(R.string.RMB_symbol) + canteenTableEntity.getBillMoney());
//        //空闲状态：显示容纳人数，名称，空闲背景图片
//
//        //预定：显示预定人数（暂时无此字段），可容纳人数、账单金额,预定人数（大）
//
//        //占用（结清）：指餐台账单已经全部付清，显示：结清icon,餐台名称、结清时间、实收金额。
//        switch (canteenTableEntity.tableStatus) {
//            case 0:
//                holder.setVisible(R.id.tv_make_time, false);
//                holder.setVisible(R.id.tv_person_num, false);
//                holder.setVisible(R.id.tv_seat_num, false);
//                //桌台人数
//                if (!TextUtils.isEmpty(canteenTableEntity.seatNum)) {
//                    holder.setText(R.id.tv_money, canteenTableEntity.seatNum + mContext.getResources().getString(R.string.man));
//                }
//                //背景
//                holder.setBackgroundRes(R.id.rl_table_root, R.drawable.bg_table_free);
//                break;
//            case 1:
//            case 2:
//            case 3:
//                //锁定小icon
//                holder.setVisible(R.id.tv_locked, true);
//                holder.setImageResource(R.id.tv_locked, R.drawable.ic_table_locked);
//                holder.setBackgroundRes(R.id.rl_table_root, R.drawable.bg_table_unavailable);
//                break;
//            case 4:
//                holder.setVisible(R.id.tv_person_num, false);
//                holder.setVisible(R.id.tv_seat_num, false);
//
//                holder.setVisible(R.id.tv_locked, true);
//                holder.setImageResource(R.id.tv_locked, R.drawable.ic_table_settled);
//                holder.setText(R.id.tv_make_time, String.valueOf(AppDateUtil.getTimeStamp(canteenTableEntity.billTime, AppDateUtil.HH_MM)));
//                holder.setText(R.id.tv_money, canteenTableEntity.getReceiveAmount());
//                break;
//            case 5:
//                holder.setBackgroundRes(R.id.rl_table_root, R.drawable.bg_table_ordering);
//                break;
//        }
        //设置选中效果
        if (mSelectedPos == mActualPos) {
            rbTableCheck.setChecked(true);
        } else {
            rbTableCheck.setChecked(false);
        }
    }

    /**
     * 设置当前选中的item的位置
     *
     * @param mActualPos
     */
    public void setSelectItem(int mActualPos) {
        this.mSelectedPos = mActualPos;
    }

}
