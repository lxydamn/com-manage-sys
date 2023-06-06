package com.sql.cms.service;

import com.sql.cms.pojo.Brand;
import com.sql.cms.pojo.Commodity;

import java.util.List;
import java.util.Map;

public interface CommodityService {

    List<Commodity> getAll();
    Map<String, String> deleteOne(Map<String, String> map);

    Map<String,String> insertOne(Map<String, Object> map);

    Map<String, String> updateOne(Map<String, Object> map);
}
