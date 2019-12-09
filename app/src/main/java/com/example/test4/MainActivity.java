package com.example.test4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.test4.MVPActivity.ChooseCityActivity;
import com.example.test4.MVPActivity.Test_UI_Activity;
import com.example.test4.MVPActivity.setTingActivity;
import com.google.gson.Gson;
import com.heweather.plugin.view.HeContent;
import com.heweather.plugin.view.HeWeatherConfig;
import com.heweather.plugin.view.HorizonView;
import com.heweather.plugin.view.LeftLargeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNowCity;
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.basic.Update;
import interfaces.heweather.com.interfacesmodule.bean.grid.forecast.GridForecast;
import interfaces.heweather.com.interfacesmodule.bean.grid.forecast.GridForecastBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.HourlyBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class MainActivity extends AppCompatActivity {

    // View 组件创建

    private ScrollView weatherLayout;
    private TextView now_temText,now_weather,now_updateTime;
    private LinearLayout forecastLayout;

    private TextView aqiText,pm25Text,comfortText,carWashText,sportText;
    private ImageView bingPicImg;

    Toolbar toolbar;   // 标题栏
    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;

    public AMapLocationClient mLocationClient = null;

    // *************    onCreate  ****************** *******************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化 View
        initView();
        //应用 Toolbar标题栏
        setSupportActionBar(toolbar);

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());

        // 声明AMapLocationClientOption对象 并初始化
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果： 该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        //回调监听
        AMapLocationListener aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if(aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {

                        // ************************ 开始获取天气信息 并 将获取到的信息显示到UI界面
                        //（1）初始化用户的key和location
                        HeWeatherConfig.init("fec1da612f44499999d1d567086a05a2");

                        // 账户初始化
                        HeConfig.init("HE1912021455011649","fec1da612f44499999d1d567086a05a2");
                        //切换到免费服务域名
                     //   HeConfig.switchToFreeServerNode();

                        // ******************  执行获取天气方法 **************

                        // 逐小时天气预报  Hourly
                        getHourly(aMapLocation.getCity());

                        //切换到免费服务域名
                           HeConfig.switchToFreeServerNode();
                        //   实时天气 WeatherNow
                        getWeatherNow(aMapLocation.getCity());

                        // 空气质量 AirNow
                        getAirNow(aMapLocation.getCity());
                        // 生活指数 Lifestyle
                        getLifestyle( aMapLocation.getCity());

                        // ************************  结束 ->  *************************

                    }

                }
                else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }


        };
        // 设置监听
        mLocationClient.setLocationListener(aMapLocationListener);
        //启动定位
        mLocationClient.startLocation();


        // *************************  初始化操作 -> *****************

        // 动态申请定位权限
        // 判断是否有定位权限
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }else {

            // 一进来 ，应该调用获取位置的方法，并设置显示相关天气信息
            Toast.makeText(MainActivity.this,"进入",Toast.LENGTH_SHORT).show();
        }


        //   swipeRefreshLayout 下拉刷新事件 (获取最新天气)
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        //获取数据

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭定位
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    // 初始化 布局组件
    public void initView(){

        toolbar = findViewById(R.id.main_toolbar);
        swipeRefreshLayout = findViewById(R.id.main_swipe_refresh);
       // llView = findViewById(R.id.main_ll_view); //左侧大布局右侧双布局控件
        // 天气控件初始化
        weatherLayout = findViewById(R.id.main_weather_layout);  // 滑动布局

        now_temText = findViewById(R.id.now_tem);                //当前气温
        now_weather = findViewById(R.id.now_weather);            // 当前天气等级
        now_updateTime = findViewById(R.id.now_updateTime);

        forecastLayout = findViewById(R.id.forever_layout);      // 未来天气预报 LinearLayout

        // aqi    // pm2.5
        aqiText = findViewById(R.id.aqi_text);
        pm25Text = findViewById(R.id.pm25_text);
        // 生活建议  舒适度  洗车   运动建议
        comfortText = findViewById(R.id.comfort_text);
        carWashText = findViewById(R.id.car_wash_text);
        sportText = findViewById(R.id.sport_text);

    }

    // 初始化 toolbar  显示 按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    // 设置 Toolbar上控件的点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            // 点击城市按钮   进行切换城市 界面    **********************
            case R.id.main_toolbar_city:
                startActivity(new Intent(MainActivity.this, ChooseCityActivity.class));
                break;
                // 点击设置页面 跳转到 setTingActivity
            case R.id.main_toolbar_start_setting:
                Intent intent = new Intent(MainActivity.this, setTingActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
        return true;
    }

    // 授予或拒绝授予权限后都会回调这个方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"欢迎",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"需要定位权限才能使用",Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;

                default:
                    break;

        }
    }

    // 悬浮按钮点击事件
    public void floatingButtonClick(View view){
        Toast.makeText(this,"aaaaa",Toast.LENGTH_SHORT).show();
    }

    //     *********************   获取和风数据  **************************

    // WeatherNow 实时天气
    private void getWeatherNow(String cityName){
        HeWeather.getWeatherNow(MainActivity.this,cityName, new HeWeather.OnResultWeatherNowBeanListener() {
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
                    Basic basic = dataObject.getBasic();
                    Update update = dataObject.getUpdate();

                    //更新数据时间
                    now_updateTime.setText(update.getLoc());
                    // 定位城市
                    toolbar.setTitle(basic.getLocation());

                    // 获取温度
                 //   now_temText.setText(now.getTmp()+ "℃");
                    now_weather.setText(now.getCond_txt());

                } else {
                    //在此查看返回数据失败的原因
                    String status = dataObject.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i("TAG", "failed code: " + code);
                }
            }
        });
    }

    //逐小时预报
    private void getHourly(String cityName){
        HeWeather.getWeatherHourly(MainActivity.this, cityName, new HeWeather.OnResultWeatherHourlyBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                    Log.i("", "Weather Hourly onError: ", throwable);
            }

            @Override
            public void onSuccess(Hourly hourly) {
                Log.i("", " Weather Hourly onSuccess: " + new Gson().toJson(hourly));

                if(Code.OK.getCode().equalsIgnoreCase(hourly.getStatus())){
                    List<HourlyBase> hourly1 = hourly.getHourly();

                    if(hourly1 != null){
                        HourlyBase hourlyBase = hourly1.get(0);
                        now_temText.setText( hourlyBase.getTime());
                    }
                } else {
                    //在此查看返回数据失败的原因
                    String status = hourly.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i("TAG", "failed code: " + code);
                }

            }
        });
    }

    //空气质量 AirNow
    private void getAirNow(String cityName) {
        HeWeather.getAirNow(MainActivity.this,cityName, new HeWeather.OnResultAirNowBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("", "Weather AirNow onError: ", throwable);
            }

            @Override
            public void onSuccess(AirNow airNow) {
                Log.i("", " Weather AirNow onSuccess: " + new Gson().toJson(airNow));

                if(Code.OK.getCode().equalsIgnoreCase(airNow.getStatus())){
                    AirNowCity air_now_city = airNow.getAir_now_city();

                    aqiText.setText(air_now_city.getAqi());
                    pm25Text.setText(air_now_city.getPm25());
                }else {
                    String status = airNow.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i("TAG", "failed code: " + code);
                }
            }
        });
    }

    // 生活指数 tLifestyle
    private void getLifestyle(String cityName){
        HeWeather.getWeatherLifeStyle(MainActivity.this,cityName,new HeWeather.OnResultWeatherLifeStyleBeanListener(){

            @Override
            public void onError(Throwable throwable) {
                Log.i("", "Weather Lifestyle onError: ", throwable);
            }

            @Override
            public void onSuccess(Lifestyle lifestyle) {

                Log.i("", " Weather LifeStyle onSuccess: " + new Gson().toJson(lifestyle));

                if(Code.OK.getCode().equalsIgnoreCase(lifestyle.getStatus())){

                    // comf舒适度     cw洗车     sport运动

                    // LifestyleBean 逐小时天气	List<Lifestyle>

                    List<LifestyleBase> lifestyle1 = lifestyle.getLifestyle();
                    LifestyleBase lifestyleBase = lifestyle1.get(0);
                    Basic basic = lifestyle.getBasic();
                    //生活指数详细描述
                    String txt = lifestyleBase.getTxt();
                    comfortText.setText(txt);
                    carWashText.setText("经度:"+ basic.getLon());
                    sportText.setText("纬度:"+ basic.getLat());


                }else {
                    String status = lifestyle.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i("TAG", "failed code: " + code);
                }

            }

        });

    }



}
