package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.repositories.CustomerRepository;
import com.lambdaschool.javaorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value="customerServices")
public class CustomerServiceImpl implements CustomerServices
{
    @Autowired

    private CustomerRepository customerRepository;

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> customerList = new ArrayList<>();
        customerRepository.findAll().iterator().forEachRemaining(customerList::add);
        return customerList;
    }

    @Override
    public Customer getCustomerById(long id)
    {
        return customerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
    }

    @Override
    public List<Customer> findByNameLike(String likename)
    {
       List<Customer> customerList = customerRepository.findCustomerByCustnameContainingIgnoringCase(likename);

        return customerList;
    }

    @Override
    public List<OrderCounts> getOrderCounts()
    {
        List<OrderCounts> orderCounts = customerRepository.getOrderCounts();
        return orderCounts;
    }
}
