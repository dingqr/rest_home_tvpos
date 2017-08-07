package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_Discount;
import com.yonyou.hhtpos.bean.check.DiscountEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：服务台结账-折扣方案弹窗页面
 */
public class DIA_Discount {
    @Bind(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.lv_discount)
    ListView mListView;
    protected Dialog mDialog;
    protected View mContentView;
    protected Context mContext;
    private ADA_Discount mAdapter;

    private ArrayList<DiscountEntity> mData = new ArrayList<>();

    public DIA_Discount(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_discount, null);
        mDialog.setContentView(mContentView);
        ButterKnife.bind(this, mContentView);
        tvDialogTitle.setText(mContext.getResources().getString(R.string.string_discount));
        initListener();
        View discount_head_view = View.inflate(mContext, R.layout.discount_head_view, null);
        mListView.addHeaderView(discount_head_view);
        mAdapter = new ADA_Discount(mContext);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 传入数据
     * @param discountList
     */
    public void setData(List<DiscountEntity> discountList) {
        mAdapter.update(discountList);
    }

    private void initListener() {
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
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 710; // 设置宽度
        lp.height = 820;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }
}
