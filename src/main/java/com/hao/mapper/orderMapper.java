package com.hao.mapper;

import com.hao.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface orderMapper {
    public List<Order> getAllOrders(String openid);

    public int ChangeStatus(int id,int status);

    public int addOrder(Order order);

    public int deleteOrder(String order_id);







}
