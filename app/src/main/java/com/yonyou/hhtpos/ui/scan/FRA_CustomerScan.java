package com.yonyou.hhtpos.ui.scan;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.check.QRCodeEntity;
import com.yonyou.hhtpos.dialog.DIA_ScanCodeNew;
import com.yonyou.hhtpos.presenter.IPayResultPresenter;
import com.yonyou.hhtpos.presenter.Impl.PayResultPresenterImpl;
import com.yonyou.hhtpos.util.Constants;
import com.yonyou.hhtpos.util.EncodingHandler;
import com.yonyou.hhtpos.view.IPayResultView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 顾客扫码fragment
 * 作者：liushuofei on 2017/7/21 19:50
 */
public class FRA_CustomerScan extends BaseFragment implements IPayResultView{

    @Bind(R.id.tv_pay_type_name)
    TextView tvPayTypeName;
    @Bind(R.id.iv_qr_code)
    ImageView ivQrCode;
    @Bind(R.id.tv_unpaid_money)
    TextView tvUnpaidMoney;
    @Bind(R.id.tv_refresh_pay_status)
    TextView tvRefreshPayStatus;

    private QRCodeEntity bean;

    /**中间者 */
    private IPayResultPresenter mPayResultPresenter;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        bean = ((DIA_ScanCodeNew) getParentFragment()).getBean();

        if (null != bean) {
            // 支付方式
            tvPayTypeName.setText(StringUtil.getString(bean.getPayType()) + "（线上支付）");

            // 二维码
            try {
                ivQrCode.setImageBitmap(EncodingHandler.createQRCode(StringUtil.getString(bean.getQrCode()), 500));
            } catch (WriterException e) {
                e.printStackTrace();
            }

            // 未付款金额
            tvUnpaidMoney.setText(mContext.getString(R.string.RMB_symbol) + StringUtil.getString(bean.getUnpaidMoney()));
        }

        mPayResultPresenter = new PayResultPresenterImpl(mContext, this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_customer_scan;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @OnClick(R.id.tv_refresh_pay_status)
    public void onClick() {
        if (null != bean){
            mPayResultPresenter.requestPayResult(Constants.SHOP_ID, bean.getTableBillId());
        }
    }

    @Override
    public void requestPayResult(boolean isPaid) {
        if (isPaid){
            CommonUtils.makeEventToast(mContext, "支付成功", false);
            ((DIA_ScanCodeNew)getParentFragment()).dismiss();
        }else {
            CommonUtils.makeEventToast(mContext, "支付未完成", false);
        }
    }
}

