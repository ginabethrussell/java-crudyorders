package com.lambdaschool.javaorders.repositories;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.views.OrderCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
   List<Customer> findCustomerByCustnameContainingIgnoringCase(String likename);

   @Query(value = "SELECT c.custname AS customer, count(o.ordnum) AS orders " +
       "FROM customers c LEFT JOIN orders o " +
       "ON c.custcode = o.custcode " +
       "GROUP BY c.custname " +
       "ORDER BY orders DESC", nativeQuery = true)
   List<OrderCounts> getOrderCounts();
}
