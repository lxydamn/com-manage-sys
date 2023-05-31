package com.sql.cms;

import com.sql.cms.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmsApplicationTests {

    @Autowired
    private CustomerMapper customerMapper;
    @Test
    void contextLoads() {
        System.out.println(customerMapper.selectAll());
    }

}
