package com.sql.cms.mapper;

import com.sql.cms.pojo.ComSpecification;
import com.sql.cms.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ComSpecificationMapper {

    List<ComSpecification> selectAll();

    void insertOne(ComSpecification comSpecification);
}
