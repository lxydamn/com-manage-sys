package com.sql.cms.mapper;

import com.sql.cms.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CommodityMapper {
    List<Commodity> selectAll();

    Commodity selectById(String coNo);
    void deleteOne(Map<String, String> map);

    void insertOne(Map<String, Object> map);

    void updateOne(Map<String, Object> map);
}
