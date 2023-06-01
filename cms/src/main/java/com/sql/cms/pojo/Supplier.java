package com.sql.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    private String suNo;
    private String suName;
    private String suContact;
    private String suConTel;
    private String suIntroduce;
    private String suPwd;
}
