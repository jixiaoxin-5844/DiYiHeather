package com.example.test4.bean.sqliteBean;

import org.litepal.crud.LitePalSupport;

// 天气信息缓存
public class WeatherCache extends LitePalSupport {

    private int id;

    private String toolbar;
    private String now_temText;
    private String now_weather;
    private String now_updateTime;

    private String aqiText;
    private String pm25Text;

    private String comfortText;
    private String carWashText;
    private String sportText;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToolbar() {
        return toolbar;
    }

    public void setToolbar(String toolbar) {
        this.toolbar = toolbar;
    }

    public String getNow_temText() {
        return now_temText;
    }

    public void setNow_temText(String now_temText) {
        this.now_temText = now_temText;
    }

    public String getNow_weather() {
        return now_weather;
    }

    public void setNow_weather(String now_weather) {
        this.now_weather = now_weather;
    }

    public String getNow_updateTime() {
        return now_updateTime;
    }

    public void setNow_updateTime(String now_updateTime) {
        this.now_updateTime = now_updateTime;
    }

    public String getAqiText() {
        return aqiText;
    }

    public void setAqiText(String aqiText) {
        this.aqiText = aqiText;
    }

    public String getPm25Text() {
        return pm25Text;
    }

    public void setPm25Text(String pm25Text) {
        this.pm25Text = pm25Text;
    }

    public String getComfortText() {
        return comfortText;
    }

    public void setComfortText(String comfortText) {
        this.comfortText = comfortText;
    }

    public String getCarWashText() {
        return carWashText;
    }

    public void setCarWashText(String carWashText) {
        this.carWashText = carWashText;
    }

    public String getSportText() {
        return sportText;
    }

    public void setSportText(String sportText) {
        this.sportText = sportText;
    }

    @Override
    public String toString() {
        return "WeatherCache{" +
                "id=" + id +
                ", toolbar='" + toolbar + '\'' +
                ", now_temText='" + now_temText + '\'' +
                ", now_weather='" + now_weather + '\'' +
                ", now_updateTime='" + now_updateTime + '\'' +
                ", aqiText='" + aqiText + '\'' +
                ", pm25Text='" + pm25Text + '\'' +
                ", comfortText='" + comfortText + '\'' +
                ", carWashText='" + carWashText + '\'' +
                ", sportText='" + sportText + '\'' +
                '}';
    }
}
