package com.hao.controller;

import com.hao.pojo.Address;
import com.hao.pojo.OrderUser;
import com.hao.service.addressServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v/my/address")
public class addressController {
    @Resource
    private addressServiceImpl service;

    @GetMapping("/getAll")
    public List<Address> queryAll(@RequestParam("openid")String openid){
        List<Address> allAddress = service.getAllAddress(openid);
        return allAddress;
    }
    @RequestMapping("/addNew")
    public String addAdress(@RequestParam("id")String id,
                            @RequestParam("name")String name,
                            @RequestParam("phone")String phone,
                            @RequestParam("address")String Address,
                            @RequestParam("address_detail")String detail,
                            @RequestParam("openid")String username,
                            @RequestParam("status")String status){
        int Iid,Istatus;

        Iid = -1;


        if(status.equals("undefined")||status.equals("")){
            Istatus = 0;
        }else{

            Istatus = Integer.valueOf(status);
        }
        if(Istatus>0){
            service.SetStatus(username);
        }

        Address address = new Address(Iid, name, phone, Address, detail, username, Istatus);
        int i = service.addAddress(address);

        if(i>0){
            return "1";
        }else {
            return "0";
        }

    }

    @RequestMapping("/updateIt")
    public String update(@RequestParam("id")String id,
                         @RequestParam("name")String name,
                         @RequestParam("phone")String phone,
                         @RequestParam("address")String Address,

                         @RequestParam("address_detail")String detail,
                         @RequestParam("openid")String username,

                         @RequestParam("status")String status){
        int Iid,Istatus;
        if(id.equals("undefined")){
            Iid = -1;

        }else{
            Iid = Integer.valueOf(id);
        }
        if(status.equals("undefined")){
            Istatus = 0;
        }else{

            Istatus = Integer.valueOf(status);
        }
        if(Istatus>0){
            service.SetStatus(username);
        }

        Address address = new Address(Iid, name, phone,Address, detail, username, Istatus);
        int i = service.updateAddress(address);
        if(i>0){
            return "1";
        }else {
            return "0";
        }
    }

    @RequestMapping("/deleteIt")
    public String delete(@RequestParam("id")String id){
        int Iid;
        if(id.equals("undefined")){
            return "0";

        }else{
            Iid = Integer.valueOf(id);
        }
        int i = service.deleteAddressById(Iid);
        if(i>0){
            return "1";
        }else {
            return "0";
        }
    }
    @RequestMapping("/getMoRen")
    public OrderUser getMoRen(@RequestParam("openid")String openid){
        Date date = new Date();
        List<Address> list = service.getAllAddress(openid);
        OrderUser orderUser = new OrderUser();
        for (Address address : list) {
            if(address.getStatus()==1){
                orderUser.setName(address.getName());
                orderUser.setUseraddress(address.getAddress()+address.getAddress_detail());
                orderUser.setPhone(address.getPhone());
                orderUser.setDatetimesingle(date.getTime());
                return orderUser;
            }
        }
        orderUser.setName(list.get(0).getName());
        orderUser.setUseraddress(list.get(0).getAddress()+list.get(0).getAddress_detail());
        orderUser.setPhone(list.get(0).getPhone());
        orderUser.setDatetimesingle(date.getTime());
        return orderUser;
    }
}
