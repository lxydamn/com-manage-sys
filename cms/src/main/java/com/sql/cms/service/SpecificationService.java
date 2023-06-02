package com.sql.cms.service;

import com.sql.cms.pojo.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationService {

    List<Specification> getList();
    Specification getById(String speNo);

    Map<String, String> insertOne(String speNo, String speName);
}
