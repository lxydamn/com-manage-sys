package com.sql.cms.controller;

import com.sql.cms.pojo.Category;
import com.sql.cms.pojo.Commodity;
import com.sql.cms.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @GetMapping("/api/commodity/get/all")
    public List<Commodity> getAll() {
        return commodityService.getAll();
    }

    @PostMapping("/api/commodity/insert")
    public Map<String, String> insertOne(@RequestParam Map<String, Object> map) {
        System.out.println(map);
        return commodityService.insertOne(map);
    }

    @PostMapping("/api/commodity/update")
    public Map<String, String> updateOne(@RequestParam Map<String, Object> map ) {
        System.out.println(map);
        return commodityService.updateOne(map);
    }
}
