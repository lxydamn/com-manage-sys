package com.sql.cms.service;

import com.sql.cms.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {

    List<Brand> getAll();

    Brand getById(String braNo);

    Map<String,String> insertOne(String braName, String braWebsite, String braPhoto);

    Map<String, String> updateOne(Map<String, String> map);
}
