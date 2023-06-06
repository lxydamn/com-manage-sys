package com.sql.cms.service;

import com.sql.cms.pojo.Customer;
import com.sql.cms.pojo.OrderDetail;
import com.sql.cms.pojo.Orders;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    Customer getUserById(String id);

    List<Orders> getRecord(Map<String, String> map);

    List<Map<String, Object>> getDetail(Map<String, String> map);

    Map<String, String> submitOrder(Map<String, String> map);

    Map<String, String> customerLogin(String id, String password);

    Map<String, String> customerRegister(String id, String password, String confirmedPassword);
}
