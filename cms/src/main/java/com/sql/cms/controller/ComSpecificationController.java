package com.sql.cms.controller;

import com.sql.cms.pojo.Category;
import com.sql.cms.pojo.ComSpecification;
import com.sql.cms.service.ComSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ComSpecificationController{
    @Autowired
    private ComSpecificationService comSpecificationService;

    @GetMapping("/api/comspe/get/all")
    public List<ComSpecification> getAll() {
        return comSpecificationService.getAll();
    }
    @PostMapping("/api/comspe/insert")
    public Map<String, String> insertOne(@RequestParam Map<String, String> map) {
        return comSpecificationService.insertOne(map);
    }

    @PostMapping("/api/comspe/update")
    public Map<String, String> updateOne(@RequestParam Map<String, String> map ) {
        return comSpecificationService.updateOne(map);
    }
}
