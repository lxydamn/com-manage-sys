package com.sql.cms.mapper;

import com.sql.cms.pojo.Customer;
import com.sql.cms.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface OrderDetailMapper {
    List<OrderDetail> selectAll();

    List<Map<String, Object>> selectByOrder(String ordNo);
    void insertOne(OrderDetail orderDetail);
}
