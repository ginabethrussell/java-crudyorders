package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value="orderServices")
public class OrderServiceImpl implements OrderServices
{
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @Override
    public Order save(Order order)
    {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(long id)
    {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found"));
        return order;
    }

    @Override
    public List<Order> findByAdvanceAmount()
    {
        List<Order> allOrders = new ArrayList<>();
        orderRepository.findAll().iterator().forEachRemaining(allOrders::add);
        List<Order> orderAdvAmt = new ArrayList<>();

        for (Order order : allOrders)
        {
           if(order.getAdvanceamount() > 0)
           {
               orderAdvAmt.add(order);
           }
        }
        return orderAdvAmt;
    }
}
