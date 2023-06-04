package com.sql.cms.controller;

import com.sql.cms.pojo.Specification;
import com.sql.cms.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @GetMapping("/api/specification/get/all")
    public List<Specification> getAll() {
        return specificationService.getList();
    }

    @GetMapping("/api/specification/id")
    public Specification getById(@RequestParam Map<String, String> map) {
        String speNo = map.get("spe_no");
        return specificationService.getById(speNo);
    }

    @PostMapping("/api/specification/insert")
    public Map<String, String> insertOne(@RequestParam Map<String, String> map) {
        String speNo = map.get("speNo");
        String speName = map.get("speName");
        return specificationService.insertOne(speNo, speName);
    }

    @PostMapping("/api/specification/update")
    public Map<String, String> updateOne(@RequestParam Map<String, String> map) {
        return specificationService.updateOne(map);
    }
}
