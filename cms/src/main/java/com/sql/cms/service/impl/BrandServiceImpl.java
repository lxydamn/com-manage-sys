package com.sql.cms.service.impl;

import com.sql.cms.mapper.BrandMapper;
import com.sql.cms.pojo.Brand;
import com.sql.cms.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    @Override
    public List<Brand> getAll() {
        return brandMapper.selectAll();
    }

    @Override
    public Brand getById(String braName) {

        return brandMapper.selectById(braName);
    }

    @Override
    public Map<String, String> deleteOne(Map<String , String > map) {
        Map<String, String> resp = new HashMap<>();

        try {
            brandMapper.deleteOne(map);
        } catch (Exception e) {
            resp.put("error_info","删除失败");
            return resp;
        }

        resp.put("error_info","success");
        return  resp;
    }

    @Override
    public Map<String, String> insertOne(String braName, String braWebsite, String braPhoto) {

        Map<String, String> resp = new HashMap<>();

        Brand brand = brandMapper.selectById(braName);

        if (brand != null) {
            resp.put("error_info", "已存在的品牌名");
            return resp;
        }

        brand = new Brand(braName, braWebsite, braPhoto);

        brandMapper.insertOne(brand);

        resp.put("error_info", "success");

        return resp;
    }

    @Override
    public Map<String, String> updateOne(Map<String, String> map) {
        Map<String, String> resp = new HashMap<>();
        String newBraName = map.get("newBraName");
        String oldBraName = map.get("oldBraName");
        Brand brand = brandMapper.selectById(newBraName);

        if (!newBraName.equals(oldBraName) && brand != null) {
            resp.put("error_info", "品牌名已存在");
            return resp;
        }

        brandMapper.updateOne(map);
        
        resp.put("error_info", "success");

        return resp;
    }

}
