package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
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
import com.yonyou.hhtpos.widgets.NumberKeybordView;

import java.lang.reflect.Method;
import java.util.ArrayList;


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
    private Context mContext;
    private NumberKeybordView numberGridView;
    private final ListView lvPayType;
    private String[] payTypes = {"现金支付", "畅捷支付", "支付宝", "微信支付"};
    private ArrayList<PayTypeEntity> payTypeList = new ArrayList<>();
    private final ADA_PayType payTypeAdapter;
    //字符串拼接
    private StringBuffer mBuffer;
    private InputFilter[] filters;
    private boolean isInputPoint = true;
    //当前输入的内容
    private String singleResult;
    //小数点后的位数
    private int decimalCount;

    public DIA_CollectForegift(Context context) {
        mContext = context;
        mBuffer = new StringBuffer();
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dia_collect_foregift, null);
        numberGridView = (NumberKeybordView) mContentView.findViewById(R.id.number_keybord_view);
        etMoney = (EditText) mContentView.findViewById(R.id.et_money);
        lvPayType = (ListView) mContentView.findViewById(R.id.lv_pay_type);
        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        mDialog.setContentView(mContentView);

        initData();
        initListener();

        payTypeAdapter = new ADA_PayType(mContext);
        lvPayType.setAdapter(payTypeAdapter);
        payTypeAdapter.update(payTypeList);

        //EditText禁用系统默认键盘弹出
        disableShowInput(etMoney);
        //EditText始终不弹出软件键盘
//        etMoney.setInputType(InputType.TYPE_NULL);
//
//        filters = new InputFilter[]{new CashierInputFilter()};
//        etMoney.setFilters(filters);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 0; i < payTypes.length; i++) {
            PayTypeEntity payTypeEntity = new PayTypeEntity();
            payTypeEntity.pay_type_name = payTypes[i];
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
                CommonUtils.makeEventToast(mContext, payTypeList.get(position).pay_type_name, false);
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

        //数字键盘点击回调
        numberGridView.setOnKeybordListener(new NumberKeybordView.onKeybordClickListener() {
            @Override
            public void onNumberReturn(String number) {
                //拼接后的字符串结果
                String bufferResult = mBuffer.toString();

                int mBufferLength = mBuffer.toString().length();

                //当前内容为空时
                if (TextUtils.isEmpty(bufferResult)) {
                    //(1)首位输入"."时,自动补0
                    if (number.equals(".")) {
                        singleResult = "0.";
                    } else {
                        Log.e("TAG", "111");
                        singleResult = number;
                    }
                    //当前内容不为空时
                } else {
                    if (bufferResult.contains(".")) {
                        if (number.equals(".")) {
                            singleResult = "";
                        } else {
                            //限制只能输入两位小数
                            decimalCount++;
                            if (decimalCount <= 2) {
                                singleResult = number;
                            } else {
                                singleResult = "";
                                decimalCount--;
                            }
//                            Elog.e("TAG","decimalCount=" + decimalCount);
                        }
                    } else {
//                        Log.e("TAG", "大于0的整数");
                        String et_money = etMoney.getText().toString();
                        if (et_money.length() <= 4) {
                            singleResult = number;
                        }else {
                            singleResult = "";
                        }
                    }
                }
                //当首位输入0时，输入结果无效
                if (number.equals("0") && mBufferLength == 0) {
                    singleResult = "";
                }


                mBuffer.append(singleResult);
                etMoney.setText(mBuffer.toString());
                etMoney.setSelection(mBuffer.length());

            }

            @Override
            public void clearNumber() {

                //如果当前的结果是"0."，直接清除
                if (mBuffer.toString().toString().equals("0.")) {
                    etMoney.setText("");
                    mBuffer.delete(0, 2);
                    return;
                }

                if (decimalCount > 0) {
                    decimalCount--;
                }
                if (mBuffer != null && mBuffer.length() > 0) {
                    mBuffer.deleteCharAt(mBuffer.length() - 1);
                }
                if (mBuffer != null && mBuffer.length() > 0) {
                    etMoney.setText(mBuffer.toString());
                } else {
                    etMoney.setText("");
                }
                etMoney.setSelection(mBuffer.length());
            }

            @Override
            public void clearAllNumber() {
//                Log.e("TAG", "clearAllNumber");
//                countDown = new CountDownUtils(10 * 1000, 1000);


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
        return mDialog;
    }

    /**
     * 禁止使用系统软件盘
     *
     * @param editText
     */
    public void disableShowInput(EditText editText) {

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
