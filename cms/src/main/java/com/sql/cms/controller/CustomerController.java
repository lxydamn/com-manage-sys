package com.sql.cms.controller;


import com.sql.cms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/api/customer/submit")
    public Map<String, String> submitOrder(@RequestParam Map<String, Object> map) {
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
