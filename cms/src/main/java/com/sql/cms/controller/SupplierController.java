package com.sql.cms.controller;

import com.sql.cms.pojo.OrdersGoods;
import com.sql.cms.service.CustomerService;
import com.sql.cms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/api/supplier/record")
    public List<OrdersGoods> getRecord() {
        return supplierService.getRecord();
    }

    @PostMapping("/api/supplier/login")
    public Map<String, String> supplierLogin(@RequestParam Map<String, String> map) {
        String id = map.get("id");
        String password = map.get("password");
        return supplierService.supplierLogin(id, password);
    }

    @PostMapping("/api/supplier/add/num")
    public Map<String, String> supply(@RequestParam Map<String, String> map) {
        return supplierService.supply(map);
    }
    @PostMapping("/api/supplier/register")
    public Map<String, String> supplierRegister(@RequestParam Map<String, String> map) {
        String id = map.get("id");
        String password = map.get("password");
        String confirmedPassword = map.get("confirmedPassword");
        return supplierService.supplierRegister(id, password,confirmedPassword);
    }
}
