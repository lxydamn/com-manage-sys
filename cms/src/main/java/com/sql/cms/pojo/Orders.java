package com.sql.cms.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private String ordNo;
    private String cusNo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ordTime;
    private Double ordSumPrise;
}
