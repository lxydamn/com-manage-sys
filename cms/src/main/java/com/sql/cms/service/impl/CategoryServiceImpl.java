package com.sql.cms.service.impl;

import com.sql.cms.mapper.CategoryMapper;
import com.sql.cms.pojo.Brand;
import com.sql.cms.pojo.Category;
import com.sql.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> getAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public Map<String, String> deleteOne(Map<String, String> map) {
        Map<String, String> resp = new HashMap<>();

        try {
            categoryMapper.deleteOne(map);
        } catch (Exception e) {
            resp.put("error_info","删除失败");
            return resp;
        }

        resp.put("error_info","success");
        return  resp;
    }

    @Override
    public Category getById(String catName) {
        return categoryMapper.selectById(catName);
    }

    @Override
    public Map<String, String> insertOne(String catName, String catPhoto) {
        Map<String, String> resp = new HashMap<>();

        Category category = categoryMapper.selectById(catName);

        if (category != null) {
            resp.put("error_info", "已存在的分类名");
            return resp;
        }

        category = new Category(catName, catPhoto);

        categoryMapper.insertOne(category);

        resp.put("error_info", "success");

        return resp;
    }

    @Override
    public Map<String, String> updateOne(Map<String, String> map) {
        Map<String, String> resp = new HashMap<>();
        String newCatName = map.get("newCatName");
        String oldCatName = map.get("oldCatName");
        Category category = categoryMapper.selectById(newCatName);

        if (!newCatName.equals(oldCatName) && category != null) {
            resp.put("error_info", "品牌名已存在");
            return resp;
        }

        categoryMapper.updateOne(map);

        resp.put("error_info", "success");

        return resp;
    }
}
