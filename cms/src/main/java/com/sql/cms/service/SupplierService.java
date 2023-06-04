package com.sql.cms.service;

import com.sql.cms.pojo.OrdersGoods;

import java.util.List;
import java.util.Map;

public interface SupplierService {

    List<OrdersGoods> getRecord();
    Map<String, String> supply(Map<String, String> map);
    Map<String, String> supplierLogin(String id, String password);

    Map<String, String> supplierRegister(String id, String password, String confirmedPassword);

}
