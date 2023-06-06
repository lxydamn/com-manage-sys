package com.sql.cms.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sql.cms.mapper.CustomerMapper;
import com.sql.cms.mapper.OrderDetailMapper;
import com.sql.cms.mapper.OrdersMapper;
import com.sql.cms.pojo.Customer;
import com.sql.cms.pojo.OrderDetail;
import com.sql.cms.pojo.Orders;
import com.sql.cms.service.CustomerService;
import com.sql.cms.utils.NoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Customer getUserById(String id) {
        return customerMapper.selectById(id);
    }

    @Override
    public List<Orders> getRecord(Map<String, String> map) {
        return ordersMapper.selectByCustomer(map.get("cusNo"));
    }

    @Override
    public List<OrderDetail> getDetail(Map<String, String> map) {
        return orderDetailMapper.selectByOrder(map.get("ordNo"));
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, String> submitOrder(Map<String, String> map) {
        Map<String, String> resp = new HashMap<>();
        try {
            System.out.println(map);
            String cusNo = map.get("cusNo");
            String ordNo = NoUtils.CreateRandomNo("order-");
            Date date = new Date(System.currentTimeMillis());
            JSONArray jsonArray = JSONArray.parseArray(map.get("coms"));
            List<OrderDetail> details = new ArrayList<>();

            double sum = 0;
            System.out.println(jsonArray);
            for (Object jsonObject : jsonArray) {
                jsonObject = (JSONObject) jsonObject;
                String coNo = ((JSONObject) jsonObject).getString("coNo");
                Double prise = Double.parseDouble(((JSONObject) jsonObject).getString("coPrise"));
                Integer num = Integer.parseInt(((JSONObject) jsonObject).getString("coNum"));
                double coSum = num * prise;
                details.add(new OrderDetail(
                        coNo,
                        ordNo,
                        NoUtils.CreateRandomNo("ord-del"),
                        prise,
                        num,
                        coSum
                ));
                sum += coSum;
            }

            ordersMapper.insertOne(new Orders(ordNo, cusNo, date, sum));

            for (OrderDetail detail : details) {
                orderDetailMapper.insertOne(detail);
            }

        } catch (Exception e) {
            resp.put("error_info", "订单出错，部分商品库存不足");
            throw new RuntimeException("事务回滚");
        }

        resp.putIfAbsent("error_info", "success");
        return resp;
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
