package com.sai.oabs.order.controller;

import com.sai.oabs.order.entity.Order;
import com.sai.oabs.order.service.OrderService;
import com.sai.oabs.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //controller list order
    @RequestMapping("list")
    public List<Order> list(HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        return orderService.list(user);
    }

    //controller save or update order
    @RequestMapping("save")
    public Map<String,Object> save(Order order,HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        order.setPerson(user.getUsername());
        orderService.save(order);
        Map<String,Object> result = new HashMap<>();
        result.put("code","0");
        result.put("msg","success");
        return result;
    }

    //delete order
    @RequestMapping("delete")
    public Map<String,Object> delete(Long id){
        orderService.delete(id);
        Map<String,Object> result = new HashMap<>();
        result.put("code","0");
        result.put("msg","success");
        return result;
    }


}
