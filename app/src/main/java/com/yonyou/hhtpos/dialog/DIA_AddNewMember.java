package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.hhtpos.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zj on 2017/8/8.
 * 邮箱：zjuan@yonyou.com
 * 描述：新增会员信息填写页面
 */
public class DIA_AddNewMember {
    @Bind(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private Context mContext;
    private RadioButton rbMan;
    private RadioButton rbWomen;

    public DIA_AddNewMember(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_add_member, null);
        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        rbMan = (RadioButton) mContentView.findViewById(R.id.rb_man);
        rbWomen = (RadioButton) mContentView.findViewById(R.id.rb_women);
        mDialog.setContentView(mContentView);
        ButterKnife.bind(this,mContentView);
        tvDialogTitle.setText(mContext.getResources().getString(R.string.string_input_new_member_info));
        initListener();


    }

    /**
     * 设置监听
     */
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
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 600; // 设置宽度
        lp.height = 900;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }

    public interface OnChoosStoreListener {
        void onChooseStore(String storeName);
    }

}
