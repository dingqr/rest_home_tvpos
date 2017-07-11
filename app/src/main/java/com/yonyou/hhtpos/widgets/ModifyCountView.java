package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
    private TextView currentCount;

    int tmp = 1;

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

    private void initView() {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.modify_count_view, this);
        add = (ImageButton)convertView.findViewById(R.id.ib_turn_plus);
        minus = (ImageButton)convertView.findViewById(R.id.ib_turn_minus);
        add.setOnClickListener(this);
        minus.setOnClickListener(this);
        currentCount = (TextView)convertView.findViewById(R.id.tv_current_count);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_turn_plus:
                //数量加1
                tmp = Integer.parseInt(currentCount.getText().toString());
                tmp++;
                currentCount.setText(String.valueOf(tmp));
                break;
            case R.id.ib_turn_minus:
                //数量减1
                tmp = Integer.parseInt(currentCount.getText().toString());
                if(tmp>0){
                    tmp--;
                }
                currentCount.setText(String.valueOf(tmp));
                break;
            default:
                break;
        }
    }
}
