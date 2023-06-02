package com.sql.cms.mapper;

import com.sql.cms.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommodityMapper {
    List<Commodity> selectAll();

    Commodity selectById(String coNo);

    void insertOne(Commodity commodity);
}
