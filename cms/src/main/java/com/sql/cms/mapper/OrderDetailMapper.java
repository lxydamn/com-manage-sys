package com.sql.cms.mapper;

import com.sql.cms.pojo.Customer;
import com.sql.cms.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface OrderDetailMapper {
    List<OrderDetail> selectAll();


    void insertOne(OrderDetail customer);
}
