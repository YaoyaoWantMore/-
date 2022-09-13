package com.hao.controller;

import com.hao.config.Token;
import com.hao.pojo.FeedBack;
import com.hao.pojo.Order;
import com.hao.pojo.orderBack;
import com.hao.pojo.riderUser;
import com.hao.service.feedbackServiceImpl;
import com.hao.service.orderServiceImpl;
import com.hao.service.riderServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BackController {

    @Resource
    private orderServiceImpl OrderService;
    @Resource
    private feedbackServiceImpl FeedBackService;

    @Resource
    private riderServiceImpl RiderService;

    @RequestMapping("/user/login")
    public String login(
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            Model model){
        System.out.println(username+":"+password);
        Token token = new Token(username,password,"Admin");
        Subject current = SecurityUtils.getSubject();
        try{
            current.login(token);
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名错误");
            return "login.html";

        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login.html";

        }
        return "redirect:/admin/main";
    }

    @RequestMapping("/main")
    public String getRider(Model model){
        List<riderUser> riders = RiderService.queryall();
        Map<Integer,Object> Ridermap = new HashMap<>();
        int i = 0;
        for (riderUser rider : riders) {
            Ridermap.put(i,rider);
            i++;
        }
        model.addAttribute("riders",Ridermap);

        return "index";
    }
    @RequestMapping("/paotui.html")
    public String showOrder(Model model){
        List<Order> all = OrderService.getAll();
        List<FeedBack> all1 = FeedBackService.getAll();
        Map<Integer,Object> map2 = new HashMap<>();

        Map<Integer,Object> map = new HashMap<>();
        int i = 0;
        for (Order order : all) {
            orderBack orderBack = new orderBack();
            orderBack.setOrder_code(order.getOrder_id());
            orderBack.setOrder_price(order.getOrder_price());
            orderBack.setOpenid(order.getOpenid());
            String status;
            if(order.getStatus()==0){
                status="未完成";
            }else if(order.getStatus()==1){
                status="已完成";
            }else if(order.getStatus()==2){
                status="已取消";
            }else if(order.getStatus()==3){
                status="已退款";
            }else {
                status="进行中";
            }
            orderBack.setStatus(status);
            map.put(i,orderBack);
            i++;
        }
        i = 0;
        for (FeedBack feedBack : all1) {
            map2.put(i,feedBack);
            i++;
        }
        model.addAttribute("orders",map);
        model.addAttribute("feedbacks",map2);


        return "orders";
    }
    @RequestMapping("/showinfo")
    public String showInfo(@RequestParam("id")int id,Model model){
        FeedBack one = FeedBackService.getOne(id);
        model.addAttribute("fb",one);

        System.out.println("值为"+id);
        return "questionInfo";
    }

    @RequestMapping("/deleteIt")
    @ResponseBody
    public int delete(@RequestParam("id") int id){
        System.out.println(id+"zhiwei");
        int i = FeedBackService.delete(id);

        if(i>0){
            return 1;
        }else{

            return 0;
        }
    }

    @RequestMapping("/completeIt")
    @ResponseBody
    public int complete(@RequestParam("id") int id){
        System.out.println(id+"zhiwei");
        int i = FeedBackService.complete(id);

        if(i>0){
            return 1;
        }else{

            return 0;
        }
    }

    @RequestMapping("/refound")
    @ResponseBody
    public int refound(@RequestParam("refound") int id){
        System.out.println(id+"zhiwei");
        int i = OrderService.refoundOrder(id);

        if(i>0){
            return 1;
        }else{

            return 0;
        }
    }



}
