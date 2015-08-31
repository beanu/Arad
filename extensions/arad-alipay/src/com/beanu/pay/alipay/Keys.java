///*
// * Copyright (C) 2010 The MobileSecurePay Project
// * All right reserved.
// * author: shiqun.shi@alipay.com
// *
// *  提示：如何获取安全校验码和合作身份者id
// *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
// *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
// *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
// */
//
//package com.beanu.pay.alipay;
//
////
//// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
//// 这里签名时，只需要使用生成的RSA私钥。
//// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
//public final class Keys {
//
//    //合作身份者id，以2088开头的16位纯数字
//    public static final String DEFAULT_PARTNER = "2088911416527998";
//
//    //收款支付宝账号
//    public static final String DEFAULT_SELLER = "zmhebei@126.com";
//
//    //商户私钥，自助生成
//    public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKq0SpKHhkEBfmGMa1exyR72WrYLlkVxfuXmZeTaS4vkuMd60vgiwkflrVdoDU5AQAiPEtIEwtK/6Q7ViUOTVRVekIuEgHHRbyGxUGK8U4arrGzffFJf1kc6EGA3GWL41LzecBA/lq2r4S40SNl/9tR3qKEO2qUpOUfi3ti/PXLFAgMBAAECgYAtZwyako9DxPjvSyDVz0SWBqIz3zsVt00wKqqZRvJm/+uhqupn8x7HbrA/Gj7HvFELtbokv/1tfnunZWY/4fTz7c5I95iTHM0u+pZ3OT3KAIKKmL/6QdEVPUOWGA7xKmhGPmJWGnEAr3s44C4prvoln4H3vjJ6cvE/K8B1afvHIQJBANj/VHQCGu9wirZliX9vjqk+f/R0cb8SqRG8HJYrdpiRgP04uUwBwExUauiMoyqevuSHLmu4ed6SrVeLRmg65MkCQQDJYuHxf0EVIOLLjAVQ93vJcBj1ZBf/wFPD8xuc+msSNlRhvmOsWcwweyYG8XPlpCbDUrAZBMHdqrqa1z+H8UgdAkA225XGHiIhFhp7MgAor1Mphhti+W0oGNeVHDE95Y790wsA+S4fAPJUWaMNV0CIrckkjOg3Y1Or6PjZgOQzxW/BAkEAvVAfZo5h+CK6EO7H8J012N9wFv7JMLr0A7h9tMwT60L3gAsiV9rygIDD9rL0aGQvrF4G/P3j4SWl1fmS15CSYQJAdP0NNaQnRewPNjYMBVicX8bghNcLpM84v96Gl+2ZAdmyHyV+8dBM4IgvGR6Kew0kn88QwwLb5HxpK3WWArGoWQ==";
//    public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqtEqSh4ZBAX5hjGtXscke9lq2C5ZFcX7l5mXk2kuL5LjHetL4IsJH5a1XaA1OQEAIjxLSBMLSv+kO1YlDk1UVXpCLhIBx0W8hsVBivFOGq6xs33xSX9ZHOhBgNxli+NS83nAQP5atq+EuNEjZf/bUd6ihDtqlKTlH4t7Yvz1yxQIDAQAB";
//}
