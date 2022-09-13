package com.hao.controller;

import com.hao.pojo.Order;
import com.hao.pojo.OrderShow;
import com.hao.pojo.OrderShowDetail;

import com.hao.service.orderServiceImpl;
import com.hao.util.SnowFlakeUtil;
import com.hao.util.TimeUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/v/my/order")
public class OrderController {
    @Resource
    private orderServiceImpl service;

    @RequestMapping("/getOneDetail")
    public OrderShowDetail getOrders(@RequestParam("openid") String openid, @RequestParam("ordercode") String order_id){

        OrderShowDetail order = new OrderShowDetail();

        List<Order> allOrder = service.getAllOrder(openid);
        for (Order order1 : allOrder) {
            if(order1.getOrder_id().equals(order_id)){
                    order.setOrder_id(order1.getOrder_id());
                    order.setUser_name(order1.getUser_name());
                    order.setUser_phone(order1.getUser_phone());
                    order.setUser_address(order1.getUser_address());
                    order.setOrder_price(order1.getOrder_price());
                    order.setOrder_address(order1.getOrder_address());
                    order.setOrder_detail(order1.getOrder_detail());
                    order.setOrder_time(new TimeUtil().Todate(order1.getOrder_time()));
                    break;
                }
            }



        return order;

    }
    @RequestMapping("/getAllShow")
    public List<OrderShow> getOrdersShow(@RequestParam("openid") String openid){
        List<Order> allOrder = service.getAllOrder(openid);
        List<OrderShow> list = new ArrayList<>();
        for (Order order : allOrder) {
            if(order.getStatus()!=5){
                String status;
                if(order.getStatus()==0){
                    status="待接单";
                }else if(order.getStatus()==1){
                    status="已完成";
                }else if(order.getStatus()==2){
                    status="已取消";
                }else if(order.getStatus()==3){
                    status="已退款";
                }else {
                    status="进行中";
                }
                list.add(new OrderShow(order.getId(),order.getOrder_id(),status,order.getOrder_price()));
            }

        }
        Collections.reverse(list);
        return list;

    }
    @RequestMapping("/complete")
    public String completeOrder(@RequestParam("id")String id){
        int Iid;
        if(id.equals("undefined")||id.equals("")){
            return "0";

        }else{
            Iid = Integer.valueOf(id);
        }
        int i = service.completeOrder(Iid);
        if(i>0){
            return "1";
        }else {
            return "0";
        }
    }



    @RequestMapping("/cancel")
    public String cancelOrder(@RequestParam("id")String id){
        int Iid;
        if(id.equals("undefined")||id.equals("")){
            return "0";

        }else{
            Iid = Integer.valueOf(id);
        }
        int i = service.cancelOrder(Iid);
        if(i>0){
            return "1";
        }else {
            return "0";
        }
    }


    @RequestMapping("/delete")
    public String deleteOrder(@RequestParam(value = "order_id")String order_id){

        System.out.println(order_id);

        int i = service.deleteOrder(order_id);
        System.out.println("删除情况"+i);
        if(i>0){
            return "1";
        }else {
            return "0";
        }
    }
    @RequestMapping("/addOrder")
    public int addOrder(@RequestParam("openid")String openid,
                        @RequestParam("name")String user_name,
                        @RequestParam("phone")String user_phone,
                        @RequestParam("useraddress")String user_address,
                        @RequestParam("payprice")String order_price,
                        @RequestParam("datetimesingle")String order_time,
                        @RequestParam("kuaididian")String order_address,
                        @RequestParam("qujianinfo")String order_detail


                        ){
        System.out.println("时间戳是"+order_time);
        String order_id = new SnowFlakeUtil().getId();
        int status = 0;
        Order order  = new Order(-1,order_id,user_name,user_phone,user_address,order_price,order_time,order_address,order_detail,openid,"no",status);

        int i = service.addOrder(order);
        return i;

    }

}
