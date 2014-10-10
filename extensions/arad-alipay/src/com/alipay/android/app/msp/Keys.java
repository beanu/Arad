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
    public static final String DEFAULT_PARTNER = "2088611503051273";

    //收款支付宝账号
    public static final String DEFAULT_SELLER = "heliweimin@163.com";

    //商户私钥，自助生成
    public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKz+6Hkz65A2H2vG/FrZDNJs58pxRAJgZSDykfaRiUSS2E0Vj87Kn7pz+TFUIrFQxTgh7gpYXxHg7/wRHuSluq4gmXxW6ZF0GYOIfT8UFrIIJgC1UCZFO5XOLUWaLDTubK5KQFVzRFdNxfxEjTNHAhlAyeaSvpEANiEx1AccUO9NAgMBAAECgYBJsbU6nIGGx2ePNA8gbEHoCfib5rBACuxJosvHK6w8CAoYnEzMnMh6VxkaiGrW+j9pcBrFUWv+Y8IXsPtmD6t7+j97JyUP3SEE51SpY3zs45aOzFk1CgsvpMqB5ddmKsq+ZE4AgDzeLXFmx/XIM4pYR9s9FHimDeiuJeZe4BHpQQJBAOB7v9i3Yw5LuOlm1AscMCTjRYNy2tX2WCu+IqTwzanuzVcfDaFNSQAneIjhV5EaMJjWiw/8z3T06X4X6P0nznUCQQDFSJ6OqbCketw+DsOTkRIRTDWMDsL/B/i7xx9u/nFtA3PD+cnTfjavCsUn2bSP5s9h6rFS1qPK1K8ySlnix7J5AkEA2gUWJEKnv+CnLHgkBM5Rq+HXR30rMJbat1EokI0XEa3OXIjaUgifl7i+BiLlSxXmkOn9kX91dR/QDhjmvcgFaQJAQZi+19LGYDpVxvCjyQpAhq3fVB2IasIcy1sPMZm63zN5p50DpaXeyt765SA3MG3fYMBni5LAzW1gf4lHT+d0iQJAWjiwPl9BaHzQyl+TWAu4BvLEnELgHLGfT4QG7PXQ8rX++rwEkESwUp9lXhNUEDq5A3tj1ebpM5WRCXJTE+3Rew==";
    public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
