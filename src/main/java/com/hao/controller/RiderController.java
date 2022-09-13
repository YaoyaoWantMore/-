package com.hao.controller;

import com.hao.pojo.Order;
import com.hao.pojo.OrderShow;
import com.hao.pojo.riderUser;
import com.hao.service.orderServiceImpl;
import com.hao.service.riderServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/api/v/my/rider")
public class RiderController {

    @Resource
    orderServiceImpl OrderService;
    @Resource
    riderServiceImpl riderService;

    //获取数据
    @RequestMapping("/getdata")
    public Map<String,Object> getData(@RequestParam("openid") String openid){
        Map<String,Object> map = new HashMap<>();
        riderUser rider = riderService.queryone(openid);
        if(rider==null){//未注册
            map.put("msg",0);
            return map;
        }else if(rider.getStatus()==0){
            map.put("msg",1);
            return map;
        }else {
            map.put("msg",2);
            List<Order> all = OrderService.getAll();
            List<Order> orders = new ArrayList<>();
            int moneySum = 0;
            for (Order order : all) {
                if(order.getStatus()==0){
                    orders.add(order);
                    moneySum = moneySum+Integer.parseInt(order.getOrder_price());
                }
            }
            map.put("orders",orders);
            map.put("moneySum",moneySum);
            map.put("orderNum",orders.size());
            map.put("userinfo",rider);



            return map;
        }

    }

    @PostMapping("/ToEnroll")
    public String enroll(@RequestBody Map<String,Object> map){
        riderUser rider = new riderUser();
        System.out.println((String)map.get("name")+"The value");
        rider.setName((String)map.get("name"));
        rider.setPhone((String)map.get("phone"));
        rider.setStudentId((String)map.get("studentId"));
        rider.setCollege((String)map.get("college"));
        rider.setOpenid((String)map.get("openid"));
        rider.setMoneyPackage(0);
        rider.setOrderNum(0);
        rider.setStatus(0);
        int i = riderService.enroll(rider);

        if(i>0){
            return "1";
        }else{
            return "0";
        }


    }

    @RequestMapping("/TogetOrder")
    public String TogetOrder(@RequestParam("order_id")String order_id,
                             @RequestParam("openid")String openid,
                             @RequestParam("orderNum")String orderNum){
        int i = 0;
        if(orderNum!=null){
            int num = Integer.parseInt(orderNum)+1;
            String Num = String.valueOf(num);
            i = OrderService.DoOrder(order_id, openid ,Num);
        }


        if(i>0){
            return "1";
        }else{
            return "0";
        }
    }

    @RequestMapping("/MyGettingOrder")
    public List<OrderShow> MyGettingOrder(@RequestParam("openid")String openid){
        List<OrderShow> list = new ArrayList<>();
        if(openid!=null){
            List<Order> riders = OrderService.getOrderByRider(openid);
            for (Order rider : riders) {
                String status;
                if(rider.getStatus()==0){
                    status="待接单";
                }else if(rider.getStatus()==1){
                    status="已完成";
                }else if(rider.getStatus()==2){
                    status="已取消";
                }else if(rider.getStatus()==3){
                    status="已退款";
                }else {
                    status="进行中";
                }
                list.add(new OrderShow(rider.getId(),rider.getOrder_id(),status,rider.getOrder_price()));
            }
            Collections.reverse(list);
            return list;
        }else{
            return null;
        }

    }

    @RequestMapping("/ToCancelOrder")
    public String ToCancelOrder(@RequestParam("order_id")String order_id,
                             @RequestParam("openid")String openid,
                             @RequestParam("orderNum")String orderNum){
        int i = 0;
        if(orderNum!=null){
            System.out.println(orderNum);
            int num = Integer.parseInt(orderNum)-1;
            String Num = String.valueOf(num);
            i = OrderService.RiderCancel(order_id, openid ,Num);
        }


        if(i>0){
            return "1";
        }else{
            return "0";
        }
    }







}
