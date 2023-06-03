package com.sql.cms.controller;

import com.sql.cms.pojo.Category;
import com.sql.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/api/category/get/all")
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/api/category/get/id")
    public Category getById(@RequestParam Map<String, String> map) {
        String braName = map.get("braName");
        return categoryService.getById(braName);
    }

    @PostMapping("/api/category/insert")
    public Map<String, String> insertOne(@RequestParam Map<String, String> map) {
        String catName = map.get("catName");
        String catPhoto = map.get("catPhoto");
        return categoryService.insertOne(catName,catPhoto);
    }

    @PostMapping("/api/category/update")
    public Map<String, String> updateOne(@RequestParam Map<String, String> map ) {
        return categoryService.updateOne(map);
    }

}
