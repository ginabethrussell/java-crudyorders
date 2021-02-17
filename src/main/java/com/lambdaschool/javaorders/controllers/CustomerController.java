package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.services.CustomerServices;
import com.lambdaschool.javaorders.views.OrderCounts;
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
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    private CustomerServices customerServices;

    // http://localhost:2019/customers/orders
    // Returns all customers with their orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> getAllCustomers()
    {
        List<Customer> customerList = customerServices.findAllCustomers();
        return new ResponseEntity<>(customerList,
            HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/{id}
    // Returns the customer and their orders with the given customer id
    @GetMapping(value = "/customer/{id}", produces = "application/json")
    public ResponseEntity<?> getCustomerById(@PathVariable long id)
    {
        Customer customer = customerServices.getCustomerById(id);
        return new ResponseEntity<>(customer,
            HttpStatus.OK);
    }

    // http://localhost:2019/customers/namelike/{likename}
    // Returns all customers and their orders with a customer name containing the given substring
    @GetMapping(value = "/namelike/{likename}", produces = "application/json")
    public ResponseEntity<?> getCustomerByNameLike(@PathVariable String likename)
    {
        List<Customer> customerList = customerServices.findByNameLike(likename);
        return new ResponseEntity<>(customerList,
            HttpStatus.OK);
    }

    // http://localhost:2019/customers/orders/count
    // Using a custom query, return a list of all customers with the number of orders they have placed
    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> getOrderCounts()
    {
        List<OrderCounts> orderCounts = customerServices.getOrderCounts();
        return new ResponseEntity<>(orderCounts, HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer
    // Post route to create a new customer, no body data returned, status Created
    @PostMapping(value="/customer", consumes="application/json")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer newCustomer)
    {
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);
        System.out.println(newCustomer);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{custcode}")
            .buildAndExpand(newCustomer.getCustcode())
            .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    // http://localhost:2019/customers/customer/{custcode}
    // Put route to overwrite an existing customer, no body data returned, status OK
    @PutMapping(value="/customer/{custcode}", consumes="application/json")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer existingCustomer, @PathVariable long custcode)
    {
        existingCustomer.setCustcode(custcode);
        existingCustomer = customerServices.save(existingCustomer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/{custcode}
    // Patch route to edit an existing customer, no body data returned, status OK
    @PatchMapping(value="/customer/{custcode}", consumes="application/json")
    public ResponseEntity<?> editCustomer(@RequestBody Customer customerData, @PathVariable long custcode)
    {
        customerData = customerServices.update(custcode, customerData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}






