package com.example.test4.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class WeatherImpl implements WeatherPresenters {
    private Context context;
    private WeatherInterface weatherInterface;

    public WeatherImpl(Context context, WeatherInterface weatherInterface) {
        this.context = context;
        this.weatherInterface = weatherInterface;
    }

    @Override
    public void getWeatherNow(String location) {
        HeWeather.getWeatherNow(context, location, new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("", "Weather Now onError: ", throwable);
            }

            @Override
            public void onSuccess(Now now) {
                Log.i("", " Weather LifeStyle onSuccess: " + new Gson().toJson(now));
                weatherInterface.getWeatherNow(now);
            }
        });
    }

    @Override
    public void getAirNow(String location) {
        HeWeather.getAirNow(context, location, new HeWeather.OnResultAirNowBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("", "Weather AirNow onError: ", throwable);
            }
            @Override
            public void onSuccess(AirNow airNow) {
                Log.i("", " Weather LifeStyle onSuccess: " + new Gson().toJson(airNow));
                weatherInterface.getAirNow(airNow);
            }
        });
    }
    @Override
    public void getWeatherHourly(String location) {
        HeWeather.getWeatherHourly(context, location, new HeWeather.OnResultWeatherHourlyBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("", "Weather WeatherHourly onError: ", throwable);
            }

            @Override
            public void onSuccess(Hourly hourly) {
                Log.i("", " Weather LifeStyle onSuccess: " + new Gson().toJson(hourly));
                weatherInterface.getWeatherHourly(hourly);
            }
        });

    }

    @Override
    public void getLifestyle(String location) {
        HeWeather.getWeatherLifeStyle(context, location, new HeWeather.OnResultWeatherLifeStyleBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("", "Weather WeatherLifeStyle onError: ", throwable);
            }

            @Override
            public void onSuccess(Lifestyle lifestyle) {
                Log.i("", " Weather LifeStyle onSuccess: " + new Gson().toJson(lifestyle));
                weatherInterface.getLifestyle(lifestyle);
            }
        });

    }
}
