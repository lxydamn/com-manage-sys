package com.sql.cms.service;

import com.sql.cms.pojo.Customer;

import java.util.Map;

public interface CustomerService {
    Customer getUserById(String id);

    Map<String, String> submitOrder(Map<String, Object> map);

    Map<String, String> customerLogin(String id, String password);

    Map<String, String> customerRegister(String id, String password, String confirmedPassword);
}
