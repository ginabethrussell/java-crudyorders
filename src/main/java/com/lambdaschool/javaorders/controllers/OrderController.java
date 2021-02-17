package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    private OrderServices orderServices;

    // http://localhost:2019/orders/order/{id}
    // Returns the order and its customer with the given order number
    @GetMapping(value = "/order/{id}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long id)
    {
        Order order = orderServices.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // http://localhost:2019/orders/advanceamount
    // Returns all orders with their customers that have an advanceamount greater than 0
    @GetMapping(value = "/advanceamount", produces = "application/json")
    public ResponseEntity<?> findOrderByAdvanceAmount()
    {
        List<Order> orderList = orderServices.findByAdvanceAmount();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
}
