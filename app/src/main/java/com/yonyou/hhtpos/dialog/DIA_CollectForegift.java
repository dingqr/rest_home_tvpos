package com.yonyou.hhtpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_PayType;
import com.yonyou.hhtpos.bean.PayTypeEntity;
import com.yonyou.hhtpos.util.InputManager;
import com.yonyou.hhtpos.widgets.NumberKeybordView;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.yonyou.hhtpos.R.id.et_money;


/**
 * Created by zj on 2017/6/30.
 * 邮箱：zjuan@yonyou.com
 * 描述：收预定押金弹窗
 */
public class DIA_CollectForegift {
    private EditText etMoney;
    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private Activity mContext;
    private NumberKeybordView numberGridView;
    private final ListView lvPayType;
    private String[] payTypes = {"现金支付", "畅捷支付", "支付宝", "微信支付"};
    private ArrayList<PayTypeEntity> payTypeList = new ArrayList<>();
    private final ADA_PayType payTypeAdapter;

    public DIA_CollectForegift(Activity context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dia_collect_foregift, null);
        numberGridView = (NumberKeybordView) mContentView.findViewById(R.id.number_keybord_view);
        etMoney = (EditText) mContentView.findViewById(et_money);
        lvPayType = (ListView) mContentView.findViewById(R.id.lv_pay_type);
        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        mDialog.setContentView(mContentView);

        initData();
        initListener();

        payTypeAdapter = new ADA_PayType(mContext);
        lvPayType.setAdapter(payTypeAdapter);
        payTypeAdapter.update(payTypeList);


        InputManager.getInstances(mContext).hideSoftInput(etMoney);

        //EditText禁用系统默认键盘弹出
        disableShowInput(etMoney);


        //输入限制设置
        numberGridView.setEtMoney(etMoney);
        //设置为只能输入整数
        numberGridView.setInputMode(NumberKeybordView.INTEGER);
        numberGridView.setMaxIntValue(7);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 0; i < payTypes.length; i++) {
            PayTypeEntity payTypeEntity = new PayTypeEntity();
            payTypeEntity.payType = payTypes[i];
            payTypeList.add(payTypeEntity);
        }
    }

    private void initListener() {
        //支付方式点击
        lvPayType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                payTypeAdapter.setSelectItem(position);
                payTypeAdapter.notifyDataSetChanged();
                CommonUtils.makeEventToast(mContext, etMoney.getText().toString(), false);
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
        etMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etMoney.setFocusableInTouchMode(true);
                etMoney.setCursorVisible(true);
            }
        });
    }

    public Dialog show() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 1240; // 设置宽度
        lp.height = 910;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                etMoney.setFocusableInTouchMode(true);
            }
        }, 500);
        return mDialog;
    }

    /**
     * 禁止使用系统软件盘
     *
     * @param editText
     */
    public void disableShowInput(EditText editText) {
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(etMoney.getWindowToken(), 0);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                    boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(etMoney, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
