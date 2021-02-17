package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    // http://localhost:2019/orders/order
    // Post a new order to the db
    @PostMapping(value="/order", consumes = "application/json")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);
        System.out.println(newOrder);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ordnum}")
            .buildAndExpand(newOrder.getOrdnum())
            .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value="/order/{ordnum}", consumes = "application/json")
    public ResponseEntity<?> updateFullOrder(@Valid @RequestBody Order updatedOrder, @PathVariable long ordnum)
    {
        updatedOrder.setOrdnum(ordnum);
        updatedOrder = orderServices.save(updatedOrder);
        System.out.println(updatedOrder);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value="/order/{ordnum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordnum)
    {
        orderServices.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
