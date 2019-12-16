package com.example.test4.presenter;

import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;

public interface WeatherPresenters {
    /**
     * 实况天气
     */
    void getWeatherNow(String location);

    /**
     * 空气实况
     */
    void getAirNow(String location);

    /**
     * 逐小时预报
     */
    void getWeatherHourly(String location);


    /*
     * 空气质量指数
     * */
    void getLifestyle(String location);

}
