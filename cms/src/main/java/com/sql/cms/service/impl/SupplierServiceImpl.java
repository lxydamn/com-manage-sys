package com.sql.cms.service.impl;

import com.sql.cms.mapper.SupplierMapper;
import com.sql.cms.pojo.Supplier;
import com.sql.cms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;
    @Override
    public Map<String, String> supplierLogin(String id, String password) {
        Map<String, String> resp = new HashMap<>();

        Supplier supplier = supplierMapper.selectById(id);

        if (supplier == null) {
            resp.put("error_info", "没找该用户！");
            return resp;
        }

        if (!password.equals(supplier.getSuPwd())) {
            resp.put("error_info", "用户密码错误！");
            return resp;
        }
        resp.put("username", supplier.getSuName());
        resp.put("error_info","success");

        return resp;
    }

    @Override
    public Map<String, String> supplierRegister(String id, String password, String confirmedPassword) {
        Map<String, String> resp = new HashMap<>();
        Supplier supplier = supplierMapper.selectById(id);

        if (supplier != null) {
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

        String name = "supplier-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 6);

        supplier = new Supplier(id, name, "联系方式...", "+86","简介...", password);

        supplierMapper.insertOne(supplier);

        resp.put("error_info", "success");

        return resp;
    }
}
