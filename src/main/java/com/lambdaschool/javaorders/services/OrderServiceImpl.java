package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Agent;
import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.CustomerRepository;
import com.lambdaschool.javaorders.repositories.OrderRepository;
import com.lambdaschool.javaorders.repositories.PaymentRepository;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // handles POST or PUT
    @Transactional
    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();
        if (order.getOrdnum() != 0) {
            //PUT
            orderRepository.findById(order.getOrdnum())
                .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found!"));
            newOrder.setOrdnum(order.getOrdnum());
        }
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setAdvanceamount(order.getAdvanceamount());

        Customer orderCustomer = customerRepository.findById(order.getCustomer().getCustcode())
            .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer()
                .getCustcode() + " Not Found"));
        newOrder.setCustomer(orderCustomer);

        for (Payment p : order.getPayments())
        {
            Payment newPay = paymentRepository.findById(p.getPaymentid())
                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
            newOrder.getPayments().add(newPay);
        }

        return orderRepository.save(newOrder);
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

    @Transactional
    @Override
    public void delete(long id)
    {
        if (orderRepository.findById(id).isPresent())
        {
            orderRepository.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Order " + id + " Not Found");
        }
    }
}
