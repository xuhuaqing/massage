package com.massagecommon.entity;

import lombok.Data;

@Data
public class RangeDTO {
    private String id;
    private double maxPrice;
    private double minPrice;
    private String provincialId;
    private Integer totalTimes;
    private Integer everyTime;
    private String name;
}
