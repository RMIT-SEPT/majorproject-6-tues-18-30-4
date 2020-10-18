package com.sai.oabs.order.service;

import com.sai.oabs.order.entity.Order;
import com.sai.oabs.order.repository.OrderRepository;
import com.sai.oabs.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    //list order by user
    public List<Order> list(User user){
        if ("admin".equals(user.getType())){
            return orderRepository.findAll();
        }
        return orderRepository.findAllByPerson(user.getUsername());
    }

    //save order
    public void save(Order order){
        orderRepository.save(order);
    }

    public void delete(Long id){
        orderRepository.deleteById(id);
    }

}
