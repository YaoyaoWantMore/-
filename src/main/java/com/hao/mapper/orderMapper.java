package com.hao.mapper;

import com.hao.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface orderMapper {
    public List<Order> getAllOrders(String openid);

    public List<Order> getOrdersByRider(String rider);

    public List<Order> getAll();

    public int ChangeStatus(@Param("id") int id,@Param("status")int status);

    public int addOrder(Order order);

    public int deleteOrder(String order_id);

    public int TodoOrder(@Param("rider") String rider,@Param("order_id")String order_id);

    public int RiderCancel(@Param("rider") String rider,@Param("order_id")String order_id);







}
