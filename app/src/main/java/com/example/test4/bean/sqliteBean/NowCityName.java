package com.example.test4.bean.sqliteBean;

import org.litepal.crud.LitePalSupport;

// 记录当前城市名
public class NowCityName extends LitePalSupport {

    private int id;
    private String cityName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
