package com.sql.cms.service;

import java.util.List;
import java.util.Map;

public interface ComSpecificationService {
    List<com.sql.cms.pojo.ComSpecification> getAll();
    Map<String, String> insertOne(Map<String, String> map);
    Map<String, String> updateOne(Map<String, String> map);
}
