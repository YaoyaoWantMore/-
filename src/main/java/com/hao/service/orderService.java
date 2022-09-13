package com.hao.service;

import com.hao.pojo.Order;

import java.util.List;

public interface orderService {
    public List<Order> getAllOrder(String openid);

    public List<Order> getOrderByRider(String rider);

    public List<Order> getAll();

    public int addOrder(Order order);

    public int completeOrder(int id);

    public int cancelOrder(int id);

    public int refoundOrder(int id);

    public int DoOrder(String order_id,String rider,String orderNum);

    public int RiderCancel(String order_id,String rider,String orderNum);

    public int deleteOrder(String order_id);
}
