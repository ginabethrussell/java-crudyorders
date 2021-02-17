package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Agent;
import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.AgentRepository;
import com.lambdaschool.javaorders.repositories.CustomerRepository;
import com.lambdaschool.javaorders.repositories.PaymentRepository;
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

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // For PUT and POST requests
    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if(customer.getCustcode() != 0)
        {
            customerRepository.findById(customer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));
            newCustomer.setCustcode(customer.getCustcode());
        }

        // assign string types from customer object
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setWorkingarea(customer.getWorkingarea());

        // assign double values from customer object
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());

        // handle collections
        // assign agent from agentrepository
        Agent customerAgent = agentRepository.findById(customer.getAgent()
            .getAgentcode())
            .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent()
                .getAgentcode() + " Not Found"));
        newCustomer.setAgent(customerAgent);

        // assign orders to customer
        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders())
        {
            Order newOrder = new Order();
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setCustomer(newCustomer);

            for (Payment p : o.getPayments())
            {
                Payment newPay = paymentRepository.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                newOrder.getPayments().add(newPay);
            }
            newCustomer.getOrders().add(newOrder);
        }


        return customerRepository.save(newCustomer);
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

    @Transactional
    @Override
    public Customer update(
        long id,
        Customer customer)
    {
        Customer currentCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));

        // if data is present, overwrite currentCustomer
        // set string types from customer object

        if (customer.getCustname() != null){
            currentCustomer.setCustname(customer.getCustname());
        }
        if (customer.getCustcity() != null){
            currentCustomer.setCustcity(customer.getCustcity());
        }
        if (customer.getCustcountry() != null){
            currentCustomer.setCustcountry(customer.getCustcountry());
        }
        if (customer.getGrade() != null){
            currentCustomer.setGrade(customer.getGrade());
        }
        if (customer.getPhone() != null){
            currentCustomer.setPhone(customer.getPhone());
        }
        if (customer.getWorkingarea() != null){
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }

        // use boolean flags to check to update doubles
        if (customer.hasvalueforopeningamount)
        {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if (customer.hasvalueforoutstandingamount)
        {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if (customer.hasvalueforpaymentamount)
        {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if (customer.hasvalueforreceiveamount)
        {
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }

        // handle collections
        // assign agent from agentrepository
        if (customer.getAgent().getAgentcode() != 0){
            Agent customerAgent = agentRepository.findById(customer.getAgent()
                .getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent()
                    .getAgentcode() + " Not Found"));
            currentCustomer.setAgent(customerAgent);
        }


        // assign orders to customer
        if (customer.getOrders().size() > 0)
        {
            currentCustomer.getOrders().clear();
            for (Order o : customer.getOrders())
            {
                Order newOrder = new Order();
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setOrderdescription(o.getOrderdescription());
                newOrder.setCustomer(currentCustomer);

                for (Payment p : o.getPayments())
                {
                    Payment newPay = paymentRepository.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                    newOrder.getPayments().add(newPay);
                }
                currentCustomer.getOrders().add(newOrder);
            }
        }

        return customerRepository.save(currentCustomer);
    }
}
