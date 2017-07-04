package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;

/**
 * 弹窗的input view（标题 + 二级标题 + 输入框）
 * 作者：liushuofei on 2017/7/3 15:43
 */
public class DialogInputView extends LinearLayout implements View.OnClickListener{

    private Context mContext;
    private String titleText;
    private String secondTitleText;
    private String inputHint;
    private String inputText;
    private Integer inputMode;

    private static final int MODE_EDIT = 0;//编辑模式
    private static final int MODE_EDIT_NO = 1;//不可编辑模式
    private static final int MODE_CLICK = 2;//点击模式

    private LinearLayout mInputLay;
    private TextView mTitle;
    private TextView mSecondTitle;
    private EditText mInputEdit;
    private ImageView mArrow;

    private OnClickListener onClickListener;

    public DialogInputView(Context context) {
        this(context, null);
    }

    public DialogInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialogInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputView);
        titleText = typedArray.getString(R.styleable.InputView_titleText);
        secondTitleText = typedArray.getString(R.styleable.InputView_secondTitleText);
        inputHint = typedArray.getString(R.styleable.InputView_inputHint);
        inputText = typedArray.getString(R.styleable.InputView_inputText);
        inputMode = typedArray.getInteger(R.styleable.InputView_inputMode, 0);
        initView();
    }

    private void initView(){
        // 设置方向
        this.setOrientation(LinearLayout.VERTICAL);

        // 初始化view
        View v = LayoutInflater.from(mContext).inflate(R.layout.dia_input_view, this);
        mInputLay = (LinearLayout) v.findViewById(R.id.ll_input);
        mTitle = (TextView) v.findViewById(R.id.tv_title);
        mSecondTitle = (TextView) v.findViewById(R.id.tv_second_title);
        mInputEdit = (EditText) v.findViewById(R.id.et_input);
        mArrow = (ImageView) v.findViewById(R.id.iv_arrow);

        // 赋值
        mTitle.setText(StringUtil.getString(titleText));
        mSecondTitle.setText(StringUtil.getString(secondTitleText));
        mInputEdit.setHint(StringUtil.getString(inputHint));
        mInputEdit.setText(StringUtil.getString(inputText));

        checkMode();
    }

    public String getInputText(){
        return mInputEdit.getText().toString().trim();
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    private void checkMode(){
        if (null == inputMode){
            return;
        }

        switch (inputMode){
            case MODE_EDIT:
                mArrow.setVisibility(View.GONE);
                break;
            case MODE_EDIT_NO:
                mArrow.setVisibility(View.GONE);
                mInputLay.setBackground(mContext.getResources().getDrawable(R.drawable.bg_input_edit_no));
                break;
            case MODE_CLICK:
                mInputEdit.setEnabled(false);
                break;

            default:
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_input:
                onClickListener.onClick(v);
                break;

            default:
                break;
        }
    }

    public interface OnClickListener{
        void onClick(View v);
    }
}
