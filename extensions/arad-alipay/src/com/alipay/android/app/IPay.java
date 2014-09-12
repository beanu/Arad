package com.alipay.android.app;

/**
 * 支付接口（支付宝，微信，银联）
 * Created by beanu on 14-9-12.
 */
public interface IPay {

    /**
     * 支付操作
     *
     * @param subject 支付主题-商品名称
     * @param body    内容详情-商品详情
     * @param price   商品价格
     */
    public void pay(String subject, String body, String price);

    /**
     * 支付成功后操作
     *
     * @param result 支付返回结果
     */
    abstract void paySuccess(String result);


    /**
     * 支付失败后操作
     *
     * @param result 支付返回结果
     */
    abstract void payFaild(String result);
}
