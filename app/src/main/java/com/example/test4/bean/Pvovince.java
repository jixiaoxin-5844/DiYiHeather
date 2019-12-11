package com.example.test4.bean;

import org.litepal.crud.LitePalSupport;

public class Pvovince extends LitePalSupport {

    private int id; //每个实体类都应该有的字段

    private String provinceName; //记录省的名字

    private int provinceCode; //记录省的代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

}
