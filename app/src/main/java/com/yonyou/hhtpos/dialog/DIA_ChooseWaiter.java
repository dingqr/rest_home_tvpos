package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_WaitersList;
import com.yonyou.hhtpos.bean.WaiterEntity;

import java.util.ArrayList;


/**
 * Created by zj on 2017/7/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：选择服务员弹窗
 */
public class DIA_ChooseWaiter {
    private final GridView mWaitersGridview;
    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private Context mContext;
    private ArrayList<WaiterEntity> mWaiterList;
    private final ADA_WaitersList mAdapter;

    public DIA_ChooseWaiter(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_choose_waiter, null);
        mDialog.setContentView(mContentView);

        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        mWaitersGridview = (GridView) mContentView.findViewById(R.id.waiters_gridview);

        setData();
        mAdapter = new ADA_WaitersList(mContext);
        mWaitersGridview.setAdapter(mAdapter);
        mAdapter.update(mWaiterList);

        initListener();



    }

    private void setData() {
        mWaiterList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            WaiterEntity waiterEntity = new WaiterEntity();
            waiterEntity.name = "二货"+i;
            mWaiterList.add(waiterEntity);
        }
    }

    /**
     * 设置监听
     */
    private void initListener() {
        mWaitersGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectItem(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });

    }

    public Dialog show() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 1030; // 设置宽度
        lp.height = 638;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }

}
