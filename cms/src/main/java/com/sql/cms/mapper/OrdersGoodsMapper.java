package com.sql.cms.mapper;


import com.sql.cms.pojo.OrdersGoods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrdersGoodsMapper {
    List<OrdersGoods> selectAll();

    void insertOne(OrdersGoods ordersGoods);
}
