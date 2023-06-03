package com.sql.cms.service.impl;

import com.sql.cms.mapper.SpecificationMapper;
import com.sql.cms.pojo.Category;
import com.sql.cms.pojo.Specification;
import com.sql.cms.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public List<Specification> getList() {
        return specificationMapper.selectAll();
    }

    @Override
    public Specification getById(String speNo) {
        return specificationMapper.selectById(speNo);
    }

    @Override
    public Map<String, String> insertOne(String speNo, String speName) {
        Map<String, String> resp = new HashMap<>();

        Specification specification = specificationMapper.selectById(speNo);

        if (specification != null) {
            resp.put("error_info", "已存在");
            return resp;
        }

        specification = new Specification(speNo, speName);

        specificationMapper.insertOne(specification);

        resp.put("error_info", "success");

        return resp;
    }

    @Override
    public Map<String, String> updateOne(Map<String, String> map) {
        Map<String, String> resp = new HashMap<>();
        String newSpeNo = map.get("newSpeNo");
        String oldSpeNo = map.get("oldSpeNo");
        Specification specification = specificationMapper.selectById(newSpeNo);

        if (!newSpeNo.equals(oldSpeNo) && specification != null) {
            resp.put("error_info", "品牌名已存在");
            return resp;
        }

        specificationMapper.updateOne(map);

        resp.put("error_info", "success");

        return resp;
    }
}
