package com.sql.cms.service.impl;

import com.sql.cms.mapper.CommodityMapper;
import com.sql.cms.pojo.Commodity;
import com.sql.cms.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public List<Commodity> getAll() {
        return commodityMapper.selectAll();
    }

    @Override
    public Map<String, String> deleteOne(Map<String, String> map) {

        Map<String, String> resp = new HashMap<>();
        System.out.println(map);
        try {
            commodityMapper.deleteOne(map);

        } catch (Exception e) {
            resp.put("error_info","删除失败");
            return resp;
        }

        resp.put("error_info","success");
        return  resp;
    }

    @Override
    public Map<String, String> insertOne(Map<String, Object> map) {
        Map<String, String> resp = new HashMap<>();

        Commodity commodity = commodityMapper.selectById((String) map.get("coNo"));

        if (commodity != null) {
            resp.put("error_info", "已存在的分类名");
            return resp;
        }


        commodityMapper.insertOne(map);

        resp.put("error_info", "success");

        return resp;
    }

    @Override
    public Map<String, String> updateOne(Map<String, Object> map) {
        Map<String, String> resp = new HashMap<>();
        String newCoNo = (String) map.get("newCoNo");
        String oldCoNo = (String) map.get("oldCoNo");
        Commodity commodity = commodityMapper.selectById(newCoNo);

        if (!oldCoNo.equals(newCoNo) && commodity != null) {
            resp.put("error_info", "已存在的商品编号");
            return resp;
        }

        commodityMapper.updateOne(map);

        resp.put("error_info", "success");

        return resp;
    }
}
