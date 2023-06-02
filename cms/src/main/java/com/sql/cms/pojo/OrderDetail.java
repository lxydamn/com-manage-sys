package com.sql.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private String coNo;
    private String ordNo;
    private String ordDetNo;
    private Double ordDetPrise;
    private Integer ordDetNum;
    private Double ordDetSumprise;
}
