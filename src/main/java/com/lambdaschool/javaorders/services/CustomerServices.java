package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.views.OrderCounts;

import java.util.List;

public interface CustomerServices
{
    Customer save(Customer customer);

    List<Customer> findAllCustomers();

    Customer getCustomerById(long id);

    List<Customer> findByNameLike(String likename);

    List<OrderCounts> getOrderCounts();
}
