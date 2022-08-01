package com.hao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;


@Component
@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "wx")
public class WeiConfig {
    //小程序appId
    public static String appId ;
    //商户号
    public static String mchId ;
    //前端支付后的回调地址，用来接收支付结果；是否支付成功
    public static String notifyUrl;//"https://……/wxpay/notify"
    //退款回调url
    public static String refundCallbackUrl ;// "https://……/wxpay/refundCallback";
    //商户支付秘钥
    public static String client_key;
}
