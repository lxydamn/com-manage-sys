package com.sql.cms.mapper;

import com.sql.cms.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CategoryMapper {
    List<Category> selectAll();

    Category selectById(String cat_name);

    void deleteOne(Map<String, String> map);

    void insertOne(Category category);

    void updateOne(Map<String, String> map);

}
