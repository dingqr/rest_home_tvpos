package com.yonyou.hhtpos.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.yonyou.hhtpos.ui.book.ACT_BookPreview;
import com.yonyou.hhtpos.ui.dinner.ts.ACT_Canteen;
import com.yonyou.hhtpos.ui.dinner.wd.ACT_Packing;
import com.yonyou.hhtpos.ui.dinner.wm.ACT_TakeOut;
import com.yonyou.hhtpos.ui.reserve.ACT_ReserveTableQuery;
import com.yonyou.hhtpos.ui.reserve.ACT_TobeConfirmOrder;

/**
 * 跳转工具类
 * 作者：liushuofei on 2017/7/13 10:26
 */
public class IntentUtil {

    private static final String [] functionList = {"堂食", "外带", "外卖", "待确认订单", "预订总览", "桌台预订查询", "查找会员", "交易流水", "售卖礼品卡", "菜品估清"
                                                    ,"时价菜设置", "状态设置", "员工", "交接班", "账单中心", "经营情况"};

    /**
     * 控制导航栏的跳转
     */
    public static void handleNavigationIntent(Context context, String functiionName) {
        if (TextUtils.isEmpty(functiionName))
            return;

        // 堂食
        if (functiionName.equals(functionList[0])){
            context.startActivity(new Intent(context, ACT_Canteen.class));
        }

        // 外带
        if (functiionName.equals(functionList[1])){
            context.startActivity(new Intent(context, ACT_Packing.class));
        }

        // 外卖
        if (functiionName.equals(functionList[2])){
            context.startActivity(new Intent(context, ACT_TakeOut.class));
        }

        // 待确认订单
        if (functiionName.equals(functionList[3])){
            context.startActivity(new Intent(context, ACT_TobeConfirmOrder.class));
        }

        // 预订总览
        if (functiionName.equals(functionList[4])){
            context.startActivity(new Intent(context, ACT_BookPreview.class));
        }

        // 桌台预订查询
        if (functiionName.equals(functionList[5])){
            context.startActivity(new Intent(context, ACT_ReserveTableQuery.class));
        }

        //((Activity)context).finish();
    }
}
