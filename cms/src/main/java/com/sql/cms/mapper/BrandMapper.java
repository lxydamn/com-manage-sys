package com.sql.cms.mapper;

import com.sql.cms.pojo.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BrandMapper {
    List<Brand> selectAll();
    Brand selectById(String braName);

    void insertOne(Brand brand);
}
