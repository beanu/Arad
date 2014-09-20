/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.alipay.android.app.msp;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

    //合作身份者id，以2088开头的16位纯数字
    public static final String DEFAULT_PARTNER = "2088511125157332";

    //收款支付宝账号
    public static final String DEFAULT_SELLER = "kaituochuangxiang@ktcx.cn";

    //商户私钥，自助生成
    public static final String PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALwId5nXD4k44aWLSzrDhe8K92sfLLLhm0SkHCN0bE9kLAmMLYu2F3LMR7PIMlncgSXNfSo9dc71mlnjzZwp29udrBpCw8MQaf1UDnnxlwKKznhckZITlzQH7Ov7zn0Js0IK8FqnQsJ7Bgwc3dFumg4Axj9DsBXKRRyZvOPF6ZCXAgMBAAECgYEAppy+FYP50TakwwI9Y9GSoPDPsYzLF6pO4bCk+kmUG3kAiGk8hlqQRnl6Y3RWIUgA69U8Pix9cigwX8w6CVER7O9qMIk+dMbwHYNSzkzp5iRrl83SeIBtcA7PD31k/Y1NOu9iIklhfduTSc1FPwzH6mI/MIAoo6RQQlM3uDqfCXECQQDmkoUwZ4PCUVbjtbLosfKbSiUUJrONOH4+Aok80OuG3WPsYwLwVIKAzvjCbaNvsI+Qc221wapUyTVvkagxQ//PAkEA0MT8QvVi1cLBRVUo64Zwr27mR27khnDV1jFDOnn0uaQ9xSCjLx4lZh1KCpfWENUWMnU8bPb38QZ8p9/FsbgMuQJBANs3MQRNVpsYRHko1lmgXd3Ax7H5/bnlnTkNpjc5FsTZllg35qJiZtVacm5AWSQmhMg8xnEJGQ2VY4WYwYVddkcCQC5t0KhXiF5/zJnNwab3pDHv2PjD+cDIcVYMaKYlnz51/2LKyl/dNXYLR38pm2rs6N3Z4tFc73FD/w8ang3TH8kCQC88FoBBMSIumnd0bZ15cWuqMhFHkjo8vuBLkJQ9Y41R909cxdpS9L32aNNZwtSC6Bmy6gtiflM9XPQBqnfPFaw=";
    public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
