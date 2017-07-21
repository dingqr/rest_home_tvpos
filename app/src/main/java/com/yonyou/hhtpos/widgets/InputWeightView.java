package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.WeightEntity;


/**
 * Created by ybing on 2017/7/10.
 */

public class InputWeightView extends RelativeLayout {
    /**上下文*/
    private Context mContext;
    /**页面控件*/
    private TextView tvDishUnit;
    private EditText evDishWeight;

    public InputWeightView(Context context) {
        this(context, null);
    }

    public InputWeightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputWeightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.input_weight_view, this);
        tvDishUnit = (TextView)convertView.findViewById(R.id.tv_dish_unit);
        evDishWeight = (EditText)convertView.findViewById(R.id.ev_dish_weight);
    }

    public void setData(WeightEntity weightEntity) {
        if (weightEntity!=null){
            tvDishUnit.setText(weightEntity.getUnit());
            evDishWeight.setHint(weightEntity.getHint());
        }
    }
    public String getNumber(){
        String weight = evDishWeight.getText().toString().trim();
        return StringUtil.getString(weight);
    }
}
