package com.sql.cms.mapper;

import com.sql.cms.pojo.ComSpecification;
import com.sql.cms.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ComSpecificationMapper {

    List<ComSpecification> selectAll();

    ComSpecification selectById(Map<String, String> map);
    void insertOne(Map<String,String> map);

    void updateOne(Map<String, String> map);
}
