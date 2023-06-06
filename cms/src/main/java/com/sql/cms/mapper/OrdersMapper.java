package com.sql.cms.mapper;

import com.sql.cms.pojo.Customer;
import com.sql.cms.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrdersMapper {

    List<Orders> selectAll();

    List<Orders> selectByCustomer(String cusNo);
    Orders selectById(String ord_no);

    void insertOne(Orders orders);
}
