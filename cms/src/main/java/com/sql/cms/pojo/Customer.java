package com.sql.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String cusNo;
    private String cusName;
    private String cusTel;
    private String cusSite;
    private String cusPwd;
}
