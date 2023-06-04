package com.sql.cms.service.impl;

import com.sql.cms.mapper.ComSpecificationMapper;
import com.sql.cms.pojo.ComSpecification;
import com.sql.cms.service.ComSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComSpecificationServiceImpl implements ComSpecificationService {


    @Autowired
    private ComSpecificationMapper comSpecificationMapper;

    @Override
    public List<ComSpecification> getAll() {
        return comSpecificationMapper.selectAll();
    }

    @Override
    public Map<String, String> insertOne(Map<String, String> map) {

        Map<String, String> resp = new HashMap<>();

        ComSpecification comSpecification = comSpecificationMapper.selectById(map);

        if (comSpecification != null) {
            resp.put("error_info", "已存在");
            return resp;
        }

        comSpecificationMapper.insertOne(map);

        resp.put("error_info", "success");

        return resp;
    }

    @Override
    public Map<String, String> updateOne(Map<String, String> map) {

        Map<String, String> resp = new HashMap<>();

        comSpecificationMapper.updateOne(map);

        resp.put("error_info", "success");

        return resp;
    }
}
