package com.sql.cms.controller;

import com.sql.cms.pojo.Brand;
import com.sql.cms.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/api/brand/delete")
    public Map<String, String> deleteBrand(@RequestParam Map<String, String> map) {
        return brandService.deleteOne(map);
    }

    @GetMapping("/api/brand/get/all")
    public List<Brand> getAll() {
        return brandService.getAll();
    }

    @GetMapping("/api/brand/get/id")
    public Brand getById(@RequestParam Map<String, String> map) {
        String braName = map.get("braName");
        return brandService.getById(braName);
    }

    @PostMapping("/api/brand/insert")
    public Map<String, String> insertOne(@RequestParam Map<String, String> map) {
        String braName = map.get("braName");
        String braWebsite = map.get("braWebsite");
        String braPhoto = map.get("braPhoto");
        return brandService.insertOne(braName,braWebsite,braPhoto);
    }

    @PostMapping("/api/brand/update")
    public Map<String, String> updateOne(@RequestParam Map<String, String> map ) {
        return brandService.updateOne(map);
    }


}
