package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 页面控件
     */
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
        tvDishUnit = (TextView) convertView.findViewById(R.id.tv_dish_unit);
        evDishWeight = (EditText) convertView.findViewById(R.id.ev_dish_weight);
        setPoint(evDishWeight);
    }

    public void setData(WeightEntity weightEntity) {
        if (weightEntity != null) {
            tvDishUnit.setText(weightEntity.getUnit());
            evDishWeight.setHint(weightEntity.getHint());
        }
    }
    public void reset(){
        evDishWeight.setText("");
    }
    public String getNumber() {
        String weight = evDishWeight.getText().toString().trim();
        return StringUtil.getString(weight);
    }

    /**
     * 监听小数点，仅保留小数点后两位
     */

    public static void setPoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }
}
