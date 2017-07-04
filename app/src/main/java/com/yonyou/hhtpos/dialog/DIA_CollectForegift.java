package com.yonyou.hhtpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_PayType;
import com.yonyou.hhtpos.bean.PayTypeEntity;
import com.yonyou.hhtpos.util.InputManager;
import com.yonyou.hhtpos.widgets.NumberKeybordView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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

    /**
     * 金额大小及输入规则的限制的相关变量
     */
    //字符串拼接
    private StringBuffer mBuffer;
    //当前输入的内容
    private String singleResult;
    //小数点后的位数
    private int decimalCount;
    //限定小数点前的最大整数位
    private static final int MAX_INTERGER_COUNT = 4;
    //限定小数点后的位数
    private static final int POINTER_LENGTH_AFTER = 2;
    //当前可以输入的整数最大值去除一个0
    private int MEDIAN_VALUE = 1000;
    //限定最大输入金额整数字符串
    private String MAX_VALUE = "10000";
    private Timer timer;
    private MyTimerTask mTask;

    public DIA_CollectForegift(Activity context) {
        mContext = context;
        mBuffer = new StringBuffer();
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

        //数字键盘点击回调
        numberGridView.setOnKeybordListener(new NumberKeybordView.onKeybordClickListener() {
            @Override
            public void onNumberReturn(String number) {
                stop();

                //拼接后的字符串结果
                String bufferResult = mBuffer.toString();
                int mBufferLength = mBuffer.toString().length();

                //当前内容为空时
                if (TextUtils.isEmpty(bufferResult)) {
                    //(1)首位输入"."时,自动补0
                    if (number.equals(".")) {
                        singleResult = "0.";
                    } else {
//                        Log.e("TAG", "大于0的整数");
                        //大于0的整数
                        singleResult = number;
                    }
                    //当前内容不为空时
                } else {
                    //输入的有小数点的情况
                    if (bufferResult.contains(".")) {
                        if (number.equals(".")) {
                            singleResult = "";
                        } else {
                            //输入小数点后面的数--限制只能输入两位小数
                            decimalCount++;
                            if (decimalCount <= POINTER_LENGTH_AFTER) {
                                //特殊情况-输入最大值时的处理：小数点后均为0的情况
                                if (etMoney.getText().toString().equals(MAX_VALUE + ".") || etMoney.getText().toString().equals(MAX_VALUE + ".0")) {
                                    if (number.equals("0")) {
                                        singleResult = number;
                                    } else {
                                        singleResult = "";
                                        decimalCount--;
                                    }
                                    //"_._" 正常情况保留后任意数字的两位小数
                                } else {
                                    singleResult = number;
                                }
                            } else {
                                singleResult = "";
                                //输入无效-将deciamlCount重置
                                decimalCount--;
                            }
                            Elog.e("TAG", "decimalCount=" + decimalCount);
                        }
                        //不包括小数点-“.”,输入的全部为整数的情况-到此处当前至少有一个整数
                    } else {
                        String et_money = etMoney.getText().toString();
                        //MAX_INTERGER_COUNT-当前可以输入的最大的整数位数
                        if (et_money.length() <= MAX_INTERGER_COUNT) {

                            int money = Integer.parseInt(et_money);
                            if (et_money.length() == MAX_INTERGER_COUNT) {
                                if (money <= MEDIAN_VALUE) {
                                    //特殊情况处理 money==1000
                                    if (money == MEDIAN_VALUE) {
                                        if (number.equals("0") || number.equals(".")) {
                                            singleResult = number;
                                        } else {
                                            singleResult = "";
                                        }
                                        //money<1000
                                    } else {
                                        singleResult = number;
                                    }
                                    //money>1000
                                } else {
                                    //只能输入"."
                                    onlyInputPoint(number);
                                }
                            } else {
                                singleResult = number;
                            }
                            //>=五位数
                        } else {
                            //只能输入"."
                            onlyInputPoint(number);
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
                stop();
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
            public void onLongClickClearAllNumber(boolean isStop) {
                if (isStop) {
                    stop();
                } else {
                    if (!etMoney.getText().toString().equals("")) {
                        timer = new Timer();
                        mTask = new MyTimerTask();
                        timer.schedule(mTask, 0, 200); //定时器启动，隔500毫秒触发一次
                    }
                }

            }
        });
    }

    /**
     * 停止连续删除动作
     */
    private void stop() {
        if (timer != null) {
            timer.cancel();  //定时器停止
            handler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 限定当前只能输入“.”
     *
     * @param number 当前待输入的数字
     */
    private void onlyInputPoint(String number) {
        if (number.equals(".")) {
            singleResult = number;
        } else {
            singleResult = "";
        }
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

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    etMoney.setText(msg.obj.toString());
                    etMoney.setSelection(etMoney.getText().length());
//                    Log.e("进入主线程", msg.obj.toString());
                    break;
                default:
                    break;
            }
        }

        ;
    };

    //定时事件
    private class MyTimerTask extends TimerTask {
        public void run() {
            String str;
//            Log.e("进入子线程", "");
            //如果EditText中的内容为空，定时器停止,否则继续往下执行，
            if (etMoney.getText().toString().equals("")) {
                stop();
            } else {
//               //截取内容中的最后一位
//                str = etMoney.getText().toString().substring(0, etMoney.getText().length() - 1);
                if (mBuffer != null && mBuffer.length() > 0) {
                    mBuffer.deleteCharAt(mBuffer.length() - 1);
                }
                if (decimalCount > 0) {
                    decimalCount--;
                }
                str = mBuffer.toString();
                Message msg = new Message();
                msg.what = 0;
                msg.obj = str;
                handler.sendMessage(msg);  //通知handler刷新EditText中的数据
            }
        }

    }

}
