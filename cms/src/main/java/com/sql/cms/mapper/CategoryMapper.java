package com.sql.cms.mapper;

import com.sql.cms.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {
    List<Category> selectAll();

    Category selectById(String cat_name);

    void insertOne(Category category);

}