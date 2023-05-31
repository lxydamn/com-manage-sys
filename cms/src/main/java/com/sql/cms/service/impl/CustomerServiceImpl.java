package com.sql.cms.service.impl;

import com.sql.cms.mapper.CustomerMapper;
import com.sql.cms.pojo.Customer;
import com.sql.cms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public Customer getUserById(String id) {
        return customerMapper.selectById(id);
    }

    @Override
    public Map<String, String> customerLogin(String id, String password) {

        Map<String, String> resp = new HashMap<>();
        System.out.println(id);
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
}
