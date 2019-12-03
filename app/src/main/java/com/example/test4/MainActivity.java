package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class MainActivity extends AppCompatActivity {

    //温度
    public String tem;
    //湿度
    public String hum;
    //光照

    // View 组件创建
    TextView tv_tem;
    TextView tv_hum;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("天气主页");

        // 初始化 View
        initView();
        //应用 Toolbar标题栏
        setSupportActionBar(toolbar);

        getWeather();


    }
    // 初始化 布局组件
    public void initView(){
        tv_tem = findViewById(R.id.main_tv_wendu);
        tv_hum = findViewById(R.id.main_tv_shidu);
        toolbar = findViewById(R.id.main_toolbar);
    }

    // 获取 天气信息
    public void getWeather(){

        // 账户初始化
        HeConfig.init("HE1912021455011649","fec1da612f44499999d1d567086a05a2");
        //切换到免费服务域名
        HeConfig.switchToFreeServerNode();

        HeWeather.getWeatherNow(MainActivity.this, "CN101230701", Lang.CHINESE_SIMPLIFIED , Unit.METRIC , new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable e) {
                Log.i("", "Weather Now onError: ", e);
            }

            @Override
            public void onSuccess(Now dataObject) {
                Log.i("", " Weather Now onSuccess: " + new Gson().toJson(dataObject));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if ( Code.OK.getCode().equalsIgnoreCase(dataObject.getStatus()) ){
                    //此时返回数据
                    NowBase now = dataObject.getNow();

                    // 获取温度
                    tem = now.getTmp();
                    //获取湿度
                    hum = now.getHum();



                    tv_tem.setText(tem+"°");
                    tv_hum.setText(hum);


                } else {
                    //在此查看返回数据失败的原因
                    String status = dataObject.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i("TAG", "failed code: " + code);
                }
            }
        });



    }
}
