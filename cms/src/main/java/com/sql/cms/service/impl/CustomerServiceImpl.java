package com.sql.cms.service.impl;

import com.sql.cms.mapper.CustomerMapper;
import com.sql.cms.pojo.Customer;
import com.sql.cms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public Customer getUserById(String id) {
        return customerMapper.selectById(id);
    }

    @Override
    public Map<String, String> submitOrder(Map<String, Object> map) {
        System.out.println(map);
        return null;
    }

    @Override
    public Map<String, String> customerLogin(String id, String password) {

        Map<String, String> resp = new HashMap<>();

        Customer customer = customerMapper.selectById(id);

        if (customer == null) {
            resp.put("error_info", "没找该用户！");
            return resp;
        }

        if (!password.equals(customer.getCusPwd())) {
            resp.put("error_info", "用户密码错误！");
            return resp;
        }
        resp.put("username", customer.getCusName());
        resp.put("error_info","success");

        return resp;
    }

    @Override
    public Map<String, String> customerRegister(String id, String password, String confirmedPassword) {
        Map<String, String> resp = new HashMap<>();
        Customer customer = customerMapper.selectById(id);

        if (customer != null) {
            resp.put("error_info", "用户名重复");
            return resp;
        }

        if (id.length() == 0 || id.length() > 20) {
            resp.put("error_info", "用户名过长");
            return resp;
        }

        if (password.length() == 0 || password.length() > 20) {
            resp.put("error_info", "密码过长");
            return resp;
        }

        if (!password.equals(confirmedPassword)) {
            resp.put("error_info", "两次输入密码不一致");
            return resp;
        }

        resp.put("error_info", "success");

        String name = "customer-" +
                        UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 6);

        customer = new Customer(id, name, "+86", "中国...", password);

        customerMapper.insertOne(customer);

        resp.put("error_info", "success");

        return resp;
    }
}
