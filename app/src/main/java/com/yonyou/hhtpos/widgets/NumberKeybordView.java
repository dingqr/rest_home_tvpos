package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CustomKeybord;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by zj on 2017/6/30.
 * 邮箱：zjuan@yonyou.com
 * 描述：自定义数字输入键盘
 */
public class NumberKeybordView extends LinearLayout {
    private Context mContext;
    private DashGridView numberGridView;
    private ArrayList<String> numberList = new ArrayList<>();
    private ADA_CustomKeybord mAdapter;
    //输入的内容
    private String content;
    private onKeybordClickListener mKeybordListener;


    //设置输入限制
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
    private EditText etMoney;
    private MyTimerTask mTask;


    private int inputMode;
    //小数
    public static int DECIMAL = 0;
    //整数
    public static int INTEGER = 1;
    //自定义输入的最大值，并只允许两位小数
    public static int CUSTOM_DECIMAL = 2;
    private int MAX_INT_VALUE = 7;

    public NumberKeybordView(Context context) {
        this(context, null);
    }

    public NumberKeybordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberKeybordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mBuffer = new StringBuffer();
        setData();
        initView();
        initListener();
    }

    /**
     * 初始化
     */
    private void initView() {
        // 设置方向
        this.setOrientation(LinearLayout.VERTICAL);
        View view = LayoutInflater.from(mContext).inflate(R.layout.number_keybord_linear, this);
        numberGridView = (DashGridView) view.findViewById(R.id.number_gridView);
        mAdapter = new ADA_CustomKeybord(mContext);
        numberGridView.setAdapter(mAdapter);
        mAdapter.update(numberList);
    }

    public void setEtMoney(EditText etMoney) {
        this.etMoney = etMoney;
    }

    /**
     * 键盘的监听
     */
    private void initListener() {
        numberGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //清除数字按钮
                if (position == mAdapter.getCount() - 1) {
                    //每次只删除一个
                    stop();
                    clearText();
                    return;
                }
                //点击的数字内容
                content = numberList.get(position);
                stop();
                limitInputNumber(content);

            }
        });
        numberGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mAdapter.getCount() - 1) {
                    //第一次长按时响应
                    LongClickClearNumber(false);
                    view.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    LongClickClearNumber(false);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    LongClickClearNumber(true);
                                    break;
                                case MotionEvent.ACTION_CANCEL:
                                    LongClickClearNumber(true);
                                    break;
                            }
                            return true;
                        }
                    });
                }
                return false;
            }
        });
    }

    /**
     * 长按连续删除内容
     */
    private void LongClickClearNumber(boolean isStop) {
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


    /**
     * 删除操作
     */
    private void clearText() {
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


    /**
     * 设置输入类型：整数和小数
     *
     * @param inputMode
     */
    public void setInputMode(int inputMode) {
        this.inputMode = inputMode;
    }

    public void setMaxIntValue(int maxValue) {
        this.MAX_INT_VALUE = maxValue;
    }

    /**
     * 限制输入的内容
     *
     * @param number
     */
    private void limitInputNumber(String number) {
        switch (inputMode) {
            case 0:
                limitDecimal(number);
                break;
            case 1:
                limitInt(number);
                break;
            case 2:
                limitMaxAndDecimal(number);
                break;
        }
    }

    /**
     * 可设置输入的任意最大值，并只允许输入两位小数
     *
     * @param number
     */
    private void limitMaxAndDecimal(String number) {
        //拼接后的字符串结果
        String bufferResult = mBuffer.toString();
        int mBufferLength = mBuffer.toString().length();
        //限制首位输入0,直接清除
        if (number.equals("0") && mBufferLength == 0) {
            singleResult = "";
            return;
        }
        if (TextUtils.isEmpty(bufferResult)) {
            if (number.equals(".")) {
                singleResult = "0.";
            } else {
                //大于0的整数
                singleResult = number;
            }
        } else {
            singleResult = number;
        }
        //只能输入一个小数点
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
        }

        mBuffer.append(singleResult);
        etMoney.setText(mBuffer.toString());
        etMoney.setSelection(mBuffer.length());
    }


    /**
     * 限制输入的为整数
     */
    private void limitInt(String number) {
        int max_length = StringUtil.getString(MAX_INT_VALUE).length();
        //拼接后的字符串结果
        String bufferResult = mBuffer.toString();
        int mBufferLength = mBuffer.toString().length();
        //限制输入的最大位数
        if (bufferResult.length() < max_length) {
            //不允许输入小数点
            if (number.equals(".")) {
                singleResult = "";
                return;
            }
            //首位输入不为0
            if (bufferResult.equals("0")) {
                singleResult = "";
                return;
            }
            int intNumber = Integer.parseInt(number);
            if (intNumber > MAX_INT_VALUE) {
                singleResult = "";
            } else {
                singleResult = number;
            }
        } else {
            singleResult = "";
        }
        mBuffer.append(singleResult);
        etMoney.setText(mBuffer.toString());
        etMoney.setSelection(mBuffer.length());
    }

    /**
     * 可限定小数位和整数位
     *
     * @param number
     */
    private void limitDecimal(String number) {
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

    /**
     * 设置数据
     */
    private void setData() {
        for (int i = 1; i <= 12; i++) {
            if (i == 10) {
                numberList.add(".");
                continue;
            }
            if (i == 11) {
                numberList.add("0");
                continue;
            }
            if (i == 12) {
                numberList.add("");
                continue;
            }
            numberList.add(i + "");
        }
    }

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
     * 点击数字键盘的回调接口
     */
    public interface onKeybordClickListener {
        void onNumberReturn(String number);

        void clearNumber();

        //连续清除文字
        void onLongClickClearAllNumber(boolean isStop);
    }

    public void setOnKeybordListener(onKeybordClickListener mKeybordListener) {
        this.mKeybordListener = mKeybordListener;
    }
}
