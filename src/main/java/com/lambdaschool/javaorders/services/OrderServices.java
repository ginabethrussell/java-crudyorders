package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;

import java.util.List;

public interface OrderServices
{
    Order save(Order order);

    Order getOrderById(long id);

    List<Order> findByAdvanceAmount();

    void delete(long id);
}
