package com.sql.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {
    private String coNo;
    private String braName;
    private String catName;
    private String coName;
    private String coType;
    private String coJl;
    private Double coMarketProse;
    private Double coSalePrise;
    private Double coCostPrise;
    private String coPhoto;
    private String coIntroduce;
    private Integer coNum;
}
