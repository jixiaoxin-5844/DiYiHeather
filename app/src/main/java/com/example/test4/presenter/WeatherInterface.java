package com.example.test4.presenter;

import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;

public interface WeatherInterface {
    /**
     * 实况天气
     */
    void getWeatherNow(Now bean);

    /**
     * 空气实况
     */
    void getAirNow(AirNow bean);

    /**
     * 逐小时预报
     */
    void getWeatherHourly(Hourly bean);


    /*
     * 空气质量指数
     * */
    void getLifestyle(Lifestyle lifestyle);
}
