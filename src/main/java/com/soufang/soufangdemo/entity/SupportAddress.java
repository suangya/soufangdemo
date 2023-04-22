package com.soufang.soufangdemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SupportAddress {
    @Id
    @GeneratedValue
    private Integer id;
    private String belongTo;
    private String enName;
    private String cnName;
    private String level;
    private Double baiduMapIng;
    private Double baiduMapLat;

    public Integer getId() {
        return id;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public String getEnName() {
        return enName;
    }

    public String getCnName() {
        return cnName;
    }

    public String getLevel() {
        return level;
    }

    public Double getBaiduMapIng() {
        return baiduMapIng;
    }

    public Double getBaiduMapLat() {
        return baiduMapLat;
    }
}
