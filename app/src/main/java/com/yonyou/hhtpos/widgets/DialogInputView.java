package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    /**上下文 */
    private Context mContext;
    /**标题文本 */
    private String titleText;
    /**二级标题文本 */
    private String secondTitleText;
    /**输入框提示语 */
    private String inputHint;
    /**输入框文字 */
    private String inputText;
    /**输入框模式 */
    private Integer inputMode;
    /**输入框输入类型（数字、电话） */
    private Integer inputType;
    /**输入框高度 */
    private Integer inputHeight;
    /**边距 */
    private Integer marginLeft;
    private Integer marginTop;
    private Integer marginRight;
    private Integer marginBottom;

    private static final int MODE_EDIT = 0;//编辑模式
    private static final int MODE_EDIT_NO = 1;//不可编辑模式
    private static final int MODE_CLICK = 2;//点击模式

    /**界面控件 */
    private LinearLayout mRoot;
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
        inputType = typedArray.getInteger(R.styleable.InputView_inputType, 0);
        inputHeight = typedArray.getInteger(R.styleable.InputView_inputHeight, 0);
        marginLeft = typedArray.getInteger(R.styleable.InputView_marginLeft, 80);
        marginTop = typedArray.getInteger(R.styleable.InputView_marginTop, 0);
        marginRight = typedArray.getInteger(R.styleable.InputView_marginRight, 80);
        marginBottom = typedArray.getInteger(R.styleable.InputView_marginBottom, 0);

        initView();
    }

    private void initView(){
        // 设置方向
        this.setOrientation(LinearLayout.VERTICAL);

        // 初始化view
        View v = LayoutInflater.from(mContext).inflate(R.layout.dia_input_view, this);
        mRoot = (LinearLayout) v.findViewById(R.id.ll_root);
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

        // 设置Margin
        LinearLayout.LayoutParams lp = (LayoutParams) mRoot.getLayoutParams();
        lp.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        mRoot.setLayoutParams(lp);

        // 设置输入框高度
        if (inputHeight != 0){
            ViewGroup.LayoutParams params = mInputLay.getLayoutParams();
            params.height = inputHeight;
            mInputLay.setLayoutParams(params);
        }

        // 根据模式设置EditText
        checkMode();
        // 根据类型设置输入类型
        checkType();
    }

    public String getInputText(){
        return mInputEdit.getText().toString().trim();
    }

    public String getHint(){
        return mInputEdit.getHint().toString();
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    /**
     * 检测当前输入框类型（可编辑、不可编辑、点击模式）
     */
    private void checkMode(){
        if (null == inputMode)
            return;

        switch (inputMode){
            // 编辑模式
            case MODE_EDIT:
                mArrow.setVisibility(View.GONE);
                break;
            // 不可编辑模式
            case MODE_EDIT_NO:
                mArrow.setVisibility(View.GONE);
                mInputLay.setBackground(mContext.getResources().getDrawable(R.drawable.bg_input_edit_no));
                break;
            // 点击模式
            case MODE_CLICK:
                mInputEdit.setEnabled(false);
                break;

            default:
                break;
        }
    }

    /**
     * 检测当前输入类型（数字、电话）
     */
    private void checkType(){
        if (null == inputType)
            return;

        switch (inputType){
            case 1:
                mInputEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            case 2:
                mInputEdit.setInputType(InputType.TYPE_CLASS_PHONE);
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
