package com.sql.cms.mapper;

import com.sql.cms.pojo.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SupplierMapper {
    Supplier selectById(String su_no);

    void insertOne(Supplier supplier);
}
