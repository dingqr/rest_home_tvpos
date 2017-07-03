package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CustomKeybord;

import java.util.ArrayList;


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
    public onKeybordClickListener mKeybordListener;

    public NumberKeybordView(Context context) {
        this(context, null);
    }

    public NumberKeybordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberKeybordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
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

    /**
     * 键盘的监听
     */
    private void initListener() {
        numberGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //清除数字按钮
                if (position == mAdapter.getCount() - 1 && mKeybordListener != null) {
                    mKeybordListener.clearNumber();
//                    view.setOnTouchListener(new OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            switch (event.getAction()) {
//                                case MotionEvent.ACTION_DOWN :
//                                    Log.e("TAG", "MotionEvent.ACTION_DOWN");
//
//                                    mKeybordListener.clearAllNumber();
//                                    break;
//                                case MotionEvent.ACTION_MOVE:
//                                    mKeybordListener.clearAllNumber();
//                                    Log.e("TAG", "MotionEvent.ACTION_MOVE");
//                                    break;
//                                case MotionEvent.ACTION_UP:
//                                    Log.e("TAG", "MotionEvent.ACTION_UP");
//                                    mKeybordListener.clearNumber();
//                                    break;
//                                case MotionEvent.ACTION_CANCEL:
//                                    Log.e("TAG", "MotionEvent.ACTION_CANCEL");
//                                    break;
//                            }
//                            return true;
//                        }
//                    });
                    return;
                }
                //点击的数字内容
                content = numberList.get(position);

                if (mKeybordListener != null) {
                    mKeybordListener.onNumberReturn(content);
                }

            }
        });
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

    /**
     * 点击数字键盘的回调接口
     */
    public interface onKeybordClickListener {
        void onNumberReturn(String number);

        void clearNumber();
        //连续清除文字
        void clearAllNumber();
    }

    public void setOnKeybordListener(onKeybordClickListener mKeybordListener) {
        this.mKeybordListener = mKeybordListener;
    }
}
