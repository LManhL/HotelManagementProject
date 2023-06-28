package com.example.hotelmangementproject.controllers.systemmanagementcontrollers;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.firebaseServices.CustomerService;
import com.example.hotelmangementproject.models.Customer;

import java.util.List;

public class CustomerController {
    public static void getListCustomer(List<Customer> listCustomer, RecyclerView.Adapter adapter){
        CustomerService.getListCustomer(listCustomer,adapter);
    }
    public static void updateCustomer(Customer customer){
        CustomerService.updateCustomer(customer);
    }
    public static void createCustomer(Customer customer){
        CustomerService.createCustomer(customer);
    }
    public static void deleteCustomer(Customer customer){
        CustomerService.deleteCustomer(customer);
    }
}
