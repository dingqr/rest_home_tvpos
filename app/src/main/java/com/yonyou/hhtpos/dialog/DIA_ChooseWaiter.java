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
import android.widget.RadioButton;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_WaitersList;
import com.yonyou.hhtpos.bean.WaiterEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zj on 2017/7/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：选择服务员弹窗-列表无下拉和上拉
 */
public class DIA_ChooseWaiter {
    private final GridView mWaitersGridview;
    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private RadioButton rbConfirm;
    private Context mContext;
    private List<WaiterEntity> mWaiterList = new ArrayList<>();
    private ADA_WaitersList mAdapter;
    private OnWaiterSelectedListener mListener;
    private WaiterEntity waiterEntity;

    public DIA_ChooseWaiter(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_choose_waiter, null);
        rbConfirm = (RadioButton) mContentView.findViewById(R.id.rb_confirm);
        mDialog.setContentView(mContentView);

        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        mWaitersGridview = (GridView) mContentView.findViewById(R.id.waiters_gridview);

        mAdapter = new ADA_WaitersList(mContext);
        mWaitersGridview.setAdapter(mAdapter);
        initListener();

    }

    public void setData(List<WaiterEntity> waiterList) {
        mWaiterList = waiterList;
        if ( waiterList != null && waiterList.size() >= 0) {
            waiterEntity = waiterList.get(0);
        }else {
            CommonUtils.makeEventToast(mContext,"请选择服务员",false);
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
                waiterEntity = mWaiterList.get(position);

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
        rbConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null && waiterEntity != null) {
                    mListener.onWaiterSelected(waiterEntity);
                    mDialog.dismiss();
                }
            }
        });

    }

    public Dialog show() {
        mAdapter.update(mWaiterList, true);

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

    /**
     * 回调选中的服务员
     */
    public interface OnWaiterSelectedListener {
        void onWaiterSelected(WaiterEntity waiterEntity);
    }

    public void setOnWaiterSelectedListener(OnWaiterSelectedListener listener) {
        this.mListener = listener;
    }

}
