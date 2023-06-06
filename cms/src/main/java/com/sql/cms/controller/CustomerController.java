package com.sql.cms.controller;


import com.sql.cms.pojo.OrderDetail;
import com.sql.cms.pojo.Orders;
import com.sql.cms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/api/customer/record/detail")
    public List<OrderDetail> getDetail(@RequestParam Map<String, String> map) {
        return customerService.getDetail(map);
    }

    @GetMapping("/api/customer/shop/record")
    public List<Orders> getRecord(@RequestParam Map<String, String> map) {
        return customerService.getRecord(map);
    }

    @PostMapping("/api/customer/submit")
    public Map<String, String> submitOrder(@RequestParam Map<String, String> map) {
        return customerService.submitOrder(map);
    }
    @PostMapping("/api/customer/login")
    public Map<String, String> customerLogin(@RequestParam Map<String, String> map) {
        String id = map.get("id");
        String password = map.get("password");

        return customerService.customerLogin(id, password);
    }

    @PostMapping("/api/customer/register")
    public Map<String, String> customerRegister(@RequestParam Map<String, String> map) {
        String id = map.get("id");
        String password = map.get("password");
        String confirmedPassword = map.get("confirmedPassword");
        return customerService.customerRegister(id, password,confirmedPassword);
    }
}
