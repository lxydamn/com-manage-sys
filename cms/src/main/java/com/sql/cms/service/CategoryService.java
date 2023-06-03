package com.sql.cms.service;

import com.sql.cms.pojo.Brand;
import com.sql.cms.pojo.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    List<Category> getAll();

    Category getById(String catName);

    Map<String,String> insertOne(String catName, String catPhoto);

    Map<String, String> updateOne(Map<String, String> map);
}
