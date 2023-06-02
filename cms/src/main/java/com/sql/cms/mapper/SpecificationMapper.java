package com.sql.cms.mapper;

import com.sql.cms.pojo.Specification;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpecificationMapper{

    List<Specification> selectAll();
    Specification selectById(String spe_no);

    void insertOne(Specification specification);
}
