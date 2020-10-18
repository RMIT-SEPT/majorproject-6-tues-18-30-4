package com.sai.oabs.order.repository;

import com.sai.oabs.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByPerson(String orderPerson);

}
