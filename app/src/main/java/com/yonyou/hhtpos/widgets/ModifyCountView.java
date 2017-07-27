package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;


/**
 * Created by ybing on 2017/7/10.
 */

public class ModifyCountView extends LinearLayout implements  View.OnClickListener{
    /**上下文*/
    private Context mContext;
    /**页面控件*/
    private ImageButton add;
    private ImageButton minus;
    private EditText currentCount;

    int count = 1;

    public ModifyCountView(Context context) {
        this(context, null);
    }

    public ModifyCountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ModifyCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public int getCount() {
        return count;
    }

    private void initView() {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.modify_count_view, this);
        add = (ImageButton)convertView.findViewById(R.id.ib_turn_plus);
        minus = (ImageButton)convertView.findViewById(R.id.ib_turn_minus);
        add.setOnClickListener(this);
        minus.setOnClickListener(this);
        currentCount = (EditText)convertView.findViewById(R.id.tv_current_count);
    }
    public void reset(){
        currentCount.setText("1");
        currentCount.setFocusable(false);
        currentCount.setFocusableInTouchMode(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_turn_plus:
                //数量加1
                count = Integer.parseInt(currentCount.getText().toString());
                count++;
                currentCount.setText(String.valueOf(count));
                break;
            case R.id.ib_turn_minus:
                //数量减1
                count = Integer.parseInt(currentCount.getText().toString());
                if(count>0){
                    count--;
                }
                currentCount.setText(String.valueOf(count));
                break;
            default:
                break;
        }
    }
}
