package com.hao.controller;



import com.hao.util.CertUtil;
import com.hao.util.SnowFlakeUtil;
import com.hao.util.WXPayConstants;
import com.hao.util.WXPayUtil;
import com.hao.vo.WeiConfig;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WXpayController {



    /**
     * 创建订单，生成预支付交易单
     * @param paramMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/wx/placeAnOrder", produces = "application/json;charset=UTF-8")
    @CrossOrigin(origins = "*")
    public Map<String,String> wxPayment(@RequestBody Map<String,Object> paramMap) throws Exception {
        return wxPaymentService(paramMap);
    }

    /**
     * 生成预支付交易单
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map<String,String> wxPaymentService(Map<String, Object> paramMap) throws Exception{
        //用户的openId
        String openId =(String) paramMap.get("openId");
        //本次交易金额（单位：分）
        Integer money = (Integer) paramMap.get("money");
        if(openId!=null&&money>0){
            //这里执行生成订单的操作，将订单保存到数据库
            //这里假设已经保存到数据库，111为本次订单号
            String productId = new SnowFlakeUtil().getId();


            // 获取ip地址，使用的是自定义的获取ip地址方法
            String ip =WXPayUtil.getIpAddress();

            // 统一下单，以下参数都要自己确认一遍
            Map<String,String> data = new HashMap<String,String>();
            data.put("appid", WeiConfig.appId);//商家平台ID
            data.put("body", "腾讯-皮肤");//商品描述
            data.put("mch_id",WeiConfig.mchId);//商户ID
            data.put("out_trade_no", productId);//订单号
            data.put("nonce_str", WXPayUtil.generateNonceStr());//获取随机字符串
            data.put("total_fee", money.toString());//金额，单位是分
            data.put("spbill_create_ip", ip);
            data.put("notify_url", WeiConfig.notifyUrl);//支付回调url，支付后微信就会调取下面的callBack方法
            data.put("trade_type", "JSAPI");  // 支付类型
            data.put("openid",openId);//用户标识

            // 将以上10个参数传入，换取sign签名
            String sign = WXPayUtil.generateSignature(data, WeiConfig.client_key);

            // 将sign签名put进去，凑齐11个参数
            data.put("sign", sign);

            // 将所有参数(map)转xml格式，这里使用的是微信的官方方法
            String xml = WXPayUtil.mapToXml(data);

            // 统一下单接口 https://api.mch.weixin.qq.com/pay/unifiedorder 这个是固定的使用这个接口
            String unifiedOrder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            //输出一下xml文件测试一下参数拼接是否正常
            System.out.println("xml为：" + xml);


            //这边使用的是自定义工具类的发送请求方法
            String xmlStr =WXPayUtil.sendPostToStr(unifiedOrder_url,xml);

            System.out.println("xmlStr为：" + xmlStr);

            // **************以下内容是返回前端页面的json数据**************
            // 预支付id
            String prepay_id = "";


            Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
            //判断是否成功
            if(map.get("return_code").equals("SUCCESS")){
                prepay_id = (String) map.get("prepay_id");
            }else {
                System.out.println(map.get("return_msg").toString());
            }

            //将这个6个参数传给前端，固定的六个参数，前端要接收
            Map<String, String> payMap = new HashMap<String, String>();
            payMap.put("appId", WeiConfig.appId); //appId
            payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");//时间戳
            payMap.put("nonceStr", WXPayUtil.generateNonceStr()); //随机字符串
            payMap.put("signType", "MD5"); //固定写MD5就好
            payMap.put("package", "prepay_id="+prepay_id);//写法就是这样，提交格式如"prepay_id=***"

            //还是通过五个参数获取到paySign
            String paySign = WXPayUtil.generateSignature(payMap, WeiConfig.client_key);

            //再将paySign这个参数put进去，凑齐六个参数
            payMap.put("paySign", paySign);
            return payMap;
        }
        return null;
    }



    /**
     * @Title: callBack
     * @Description: 支付完成的回调函数
     * @param:
     * @return:
     */
    @RequestMapping("/wxpay/notify")
    public String callBack(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("微信支付成功,微信发送的callback信息,可以开始修改订单信息");
        InputStream is = null;
        try {
            is = request.getInputStream();// 获取请求的流信息(这里是微信发的xml格式所以只能使用流来读)
            String xml = WXPayUtil.InputStream2String(is);// 流转换为String
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);// 将微信发的xml转map

            //System.out.println("微信返回给回调函数的信息为："+xml);

            //支付成功进入
            if (notifyMap.get("result_code").equals("SUCCESS")) {

                // 告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
                response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
                // 商户订单号
                String ordersSn = notifyMap.get("out_trade_no");

                // 实际支付的订单金额:单位 分
                String amountpaid = notifyMap.get("total_fee");

                // 将分转换成元-实际支付金额:元
                BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);

                //下面做业务处理
                System.out.println("===notify===回调方法已经被调！！！");
                //比如修改下单时间、订单状态等等...


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


//--------------------------------微信退款------------------------------------------

    /**
     * 微信申请退款
     * @param param
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/wx/refund", produces = "application/json;charset=UTF-8")
    @CrossOrigin(origins = "*")
    public Map<String,Object> wxRefund(@RequestBody Map<String,Object> param)  {
        return orderARefund(param);
    }

    /**
     * 微信退款
     * 注：交易时间超过一年的订单无法提交退款
     *
     * @param param
     */
    public Map<String, Object> orderARefund(Map<String, Object> param) {
        Map<String, Object> target = new HashMap<>();
        try {
            String orderId = (String)param.get("orderId");
            Integer money = (Integer) param.get("money");

            if (orderId!=null && money > 0) {
                //填写请求参数
                Map<String, String> params = new HashMap<>();
                //小程序ID
                params.put("appid", WeiConfig.appId);
                //商户号
                params.put("mch_id", WeiConfig.mchId);
                //商户订单号
                params.put("out_trade_no", orderId);
                //商户退款单号
                params.put("out_refund_no", orderId);
                //总金额
                params.put("total_fee", String.valueOf(money));
                //退款金额
                params.put("refund_fee", String.valueOf(money));
                //退款原因
                params.put("refund_desc", "用户退款");
//                //退款结果回调地址
                params.put("notify_url",WeiConfig.refundCallbackUrl);
                //随机字符串
                params.put("nonce_str", WXPayUtil.generateNonceStr());
                //生成签名
                String sign = WXPayUtil.generateSignature(params, WeiConfig.client_key);
                params.put("sign", sign);
                //微信申请退款接口
                String wx_refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
                //将参数转换成xml格式
                String xmlStr = WXPayUtil.mapToXml(params);
                //发送双向证书请求给微信
                String resultXmlStr = CertUtil.doRefund(wx_refund_url, xmlStr);
                // 将返回的字符串转成Map集合
                Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXmlStr);

                if ("SUCCESS".equalsIgnoreCase(resultMap.get("result_code"))) {
                    //申请微信退款接口返回success，但是退款到账还需要时间；
                    target.put("returnCode", "SUCCESS");
                    target.put("returnMsg", "退款成功");
                } else {
                    target.put("returnCode", "FAIL");
                    target.put("returnMsg", resultMap.get("err_code_des"));
                }
                return target;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     *
     * 微信退款回调
     * @param
     * @param
     */
    @ResponseBody
    @RequestMapping(value = "/wxpay/refundCallback")
    @CrossOrigin(origins = "*")
    public void WXtrackRefunderrorrm2(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws  Exception{
        System.out.println("开始退款");
        InputStream inStream = httpRequest.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        //将xml转化为map
        Map<String, String> params = WXPayUtil.xmlToMap(resultxml);
        System.out.println("map1-->>"+params);
        outSteam.close();
        inStream.close();

        if (params.get("return_code").equals("SUCCESS")) {

            String req_info = params.get("req_info");

            //对加密信息进行解密
            String afterDecrypt=WXPayUtil.decryptData(req_info);

            Map<String, String> aesMap = WXPayUtil.xmlToMap(afterDecrypt);
            System.out.println("解密结果--》》"+aesMap);

            /** 以下为返回的加密字段： **/
            //  商户退款单号  是   String(64)  1.21775E+27 商户退款单号
            String out_refund_no = aesMap.get("out_refund_no");
            //  退款状态    是   String(16)  SUCCESS SUCCESS-退款成功、CHANGE-退款异常、REFUNDCLOSE—退款关闭
            String refund_status = aesMap.get("refund_status");
            //  商户订单号   是   String(32)  1.21775E+27 商户系统内部的订单号
            String out_trade_no = aesMap.get("out_trade_no");

            System.out.println("退款单号--》》"+out_refund_no);
            System.out.println("商户订单号--》》"+out_trade_no);

            if (WXPayConstants.SUCCESS.equals(refund_status)) {
                //执行自己的业务逻辑，比如修改订单状态……
            }
        }

    }

}


