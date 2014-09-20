package com.alipay.android.app;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alipay.android.app.msp.Keys;
import com.alipay.android.app.msp.Result;
import com.alipay.android.app.msp.Rsa;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 支付宝支付
 * Created by beanu on 14-9-12.
 */
public class AliPay implements IPay {

    public static final String TAG = "alipay";

    private static final int RQF_PAY = 1;
    private static final int RQF_LOGIN = 2;
    private Activity context;

    public AliPay(Activity context) {
        this.context = context;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Result result = new Result((String) msg.obj);

            switch (msg.what) {
                case RQF_PAY:
                    result.parseResult();
                    if (result.isSignOk) {
                        //检验成功
                        //TODO
                        paySuccess(result.getResult());
                    } else {
                        //检验失败
                        //TODO
                        payFaild(result.getResult());
                    }

                    break;
                case RQF_LOGIN:
                    Toast.makeText(context, result.getResult(),
                            Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void pay(String subject, String body, String price) {
        try {
            Log.i(TAG, "*******pay*******");
            String info = getNewOrderInfo(subject, body, price);
            String sign = Rsa.sign(info, Keys.PRIVATE);
            sign = URLEncoder.encode(sign);
            info += "&sign=\"" + sign + "\"&" + getSignType();
            Log.i(TAG, "start pay");
            // start the pay.
            Log.i(TAG, "info = " + info);

            final String orderInfo = info;
            new Thread() {
                public void run() {
                    com.alipay.android.app.sdk.AliPay alipay = new com.alipay.android.app.sdk.AliPay(context, mHandler);

                    //设置为沙箱模式，不设置默认为线上环境
                    //alipay.setSandBox(true);

                    String result = alipay.pay(orderInfo);

                    Log.i(TAG, "result = " + result);
                    Message msg = new Message();
                    msg.what = RQF_PAY;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            }.start();

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "AliPay Failure calling remote service",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void paySuccess(String result) {

    }

    @Override
    public void payFaild(String result) {

    }


    private String getNewOrderInfo(String subject, String body, String price) {
        StringBuilder sb = new StringBuilder();
        sb.append("partner=\"");
        sb.append(Keys.DEFAULT_PARTNER);
        sb.append("\"&out_trade_no=\"");
        sb.append(getOutTradeNo());
        sb.append("\"&subject=\"");
        sb.append(subject);
        sb.append("\"&body=\"");
        sb.append(body);
        sb.append("\"&total_fee=\"");
        sb.append(price);
//        sb.append("\"&notify_url=\"");
        // 网址需要做URL编码
//        sb.append(URLEncoder.encode("http://notify.java.jpxx.org/index.jsp"));
        sb.append("\"&service=\"mobile.securitypay.pay");
        sb.append("\"&_input_charset=\"UTF-8");
        sb.append("\"&return_url=\"");
        sb.append(URLEncoder.encode("http://m.alipay.com"));
        sb.append("\"&payment_type=\"1");
        sb.append("\"&seller_id=\"");
        sb.append(Keys.DEFAULT_SELLER);

        // 如果show_url值为空，可不传
        // sb.append("\"&show_url=\"");
        sb.append("\"&it_b_pay=\"1m");
        sb.append("\"");

        return new String(sb);
    }

    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date();
        String key = format.format(date);

        java.util.Random r = new java.util.Random();
        key += r.nextInt();
        key = key.substring(0, 15);
        Log.d(TAG, "outTradeNo: " + key);
        return key;
    }

    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
