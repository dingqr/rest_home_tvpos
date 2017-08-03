package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by ybing on 2017/8/2.
 * 邮箱：zjuan@yonyou.com
 * 描述：增值税发票实体类
 */
public class RaxInvoiceCallBackEntity implements Serializable {
    /**发票抬头*/
    private String invoiceTitle;


    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public RaxInvoiceCallBackEntity() {
    }

    @Override
    public String toString() {
        return "RaxInvoiceCallBackEntity{" +
                "invoiceTitle='" + invoiceTitle + '\'' +
                '}';
    }
}
