package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;

import java.util.List;

public interface OrderServices
{
    public Order save(Order order);

    Order getOrderById(long id);

    List<Order> findByAdvanceAmount();
}
