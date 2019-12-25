package com.example.test4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.test4.MVPActivity.for_Activity.test_CityPicker_Activity;
import com.example.test4.MVPActivity.setTingActivity;
import com.example.test4.bean.sqliteBean.HourlyBaseCache;
import com.example.test4.bean.sqliteBean.WeatherCache;
import com.example.test4.presenter.WeatherImpl;
import com.example.test4.presenter.WeatherInterface;
import com.heweather.plugin.view.HeWeatherConfig;

import org.litepal.LitePal;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNowCity;
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.basic.Update;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.HourlyBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;

public class MainActivity extends AppCompatActivity implements WeatherInterface {

    // View 组件创建

    private TextView now_temText,now_weather,now_updateTime;
    private LinearLayout forecastLayout;

    private TextView aqiText,pm25Text,comfortText,carWashText,sportText;
    private ImageView bingPicImg;

    Toolbar toolbar;   // 标题栏
    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;

    public AMapLocationClient mLocationClient = null;

    // 关于城市名
    //选择页面传回来的城市名
    String choose_cityName;
    // 定位城市
    public String locaTion_CityName ;
    //当前选择城市
    String now_CityName;

    //  缓存 使用与设置页面同一个文件来缓存
    private final String SetTing_SharedPreferencesName = "DiYi_My_SetTing" ;

    // 声名数据库的 天气实时信息 JavaBean 类
    WeatherCache weatherCache = new WeatherCache();

    // *************    onCreate  ****************** *******************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化 View
        initView();
        //应用 Toolbar标题栏
        setSupportActionBar(toolbar);
        //获取定位
        initLocation();
        //加载并显示缓存数据
        getCacheAndShowData();

        // 获取缓存的定位城市信息


        //   swipeRefreshLayout 下拉刷新事件 (获取最新天气)
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //删除天气信息数据
               // deleteAllCache();
                // 更新数据
                initData(judgeUseWay());
                // 设置它的停止
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }

    // 判断使用定位城市，还是选择城市 （默认定位城市）
    private String judgeUseWay(){

        int ic = 0 ;

        SharedPreferences pre = getSharedPreferences(SetTing_SharedPreferencesName,MODE_PRIVATE);

        ic =  pre.getInt("if_Choose_City",0);  // 获取到 是1或者余数是1 那就是使用选择的城市
        if( ic %2 == 1){

            now_CityName = pre.getString("chooseCity","北京市");

        }else {
            now_CityName = pre.getString("locationCity","北京市");
        }

        return now_CityName;

    }

    // 获取缓存的定位城市信息
    private String findCacheCity(){
        SharedPreferences pre = getSharedPreferences(SetTing_SharedPreferencesName,MODE_PRIVATE);

        return ( pre.getString("locationCity","北京市"));  //查不到时使用北京市
    }

    public void initLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        // 声明AMapLocationClientOption对象 并初始化
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果： 该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 设置监听
        mLocationClient.setLocationListener(new MyLocationListener());
        //启动定位
        mLocationClient.startLocation();
    }

    // 判断时使用定位天气还是自己选择的城市的天气
    public String choose_CityName(){

      /*  if(choose_cityName == null){
            now_CityName = locaTion_CityName;
        }else {
            now_CityName = choose_cityName;
        }*/


        return now_CityName;
    }


    // 获取数据
    private void initData(String cityName){
        //删除天气信息数据
        deleteAllCache();
        // 和风初始化
        HeWeatherConfig.init("e45535cfafe946518d2762ffc980297a");
        // 账户初始化
        HeConfig.init("HE1912101541351848", "e45535cfafe946518d2762ffc980297a");
        //切换到免费服务域名
        HeConfig.switchToFreeServerNode();
        WeatherImpl weather = new WeatherImpl(this,this);
        weather.getAirNow(cityName);
        weather.getLifestyle(cityName);
        weather.getWeatherNow(cityName);
        weather.getWeatherHourly(cityName);
    }
    //    *** *************** <-   获取和风数据     **************************

    @Override
    public void getWeatherNow(Now bean) {

        if ( Code.OK.getCode().equalsIgnoreCase(bean.getStatus()) ){
            //此时返回数据
            NowBase now = bean.getNow();
            Basic basic = bean.getBasic();
            Update update = bean.getUpdate();
            //更新数据时间
            now_updateTime.setText(update.getLoc());
            // 定位城市
            toolbar.setSubtitle(basic.getLocation());
            // 获取温度
            now_temText.setText(now.getTmp()+ "℃");
            now_weather.setText(now.getCond_txt());
            // 缓存数据
            weatherCache.setNow_temText(now.getTmp()+ "℃");
            weatherCache.setNow_weather(now.getCond_txt());
            weatherCache.setNow_updateTime(update.getLoc());
            weatherCache.setToolbar(basic.getLocation());
            weatherCache.save();

        } else {
            //在此查看返回数据失败的原因
            String status = bean.getStatus();
            Code code = Code.toEnum(status);
            Log.i("TAG", "failed code: " + code);
        }

    }

    @Override
    public void getAirNow(AirNow bean) {

        if(Code.OK.getCode().equalsIgnoreCase(bean.getStatus())){
            AirNowCity air_now_city = bean.getAir_now_city();
            aqiText.setText(air_now_city.getAqi());
            pm25Text.setText(air_now_city.getPm25());
            //缓存数据
            weatherCache.setAqiText(air_now_city.getAqi());
            weatherCache.setPm25Text(air_now_city.getPm25());
            weatherCache.save();
        }else {
            String status = bean.getStatus();
            Code code = Code.toEnum(status);
            Log.i("TAG", "failed code: " + code);
        }
    }

    @Override
    public void getWeatherHourly(Hourly bean) {
        if(Code.OK.getCode().equalsIgnoreCase(bean.getStatus())){
            List<HourlyBase> hourly1 = bean.getHourly();
            forecastLayout.removeAllViews();

            for (int i = 0; i < 8; i++) {
                HourlyBaseCache hourlyBaseCache = new HourlyBaseCache();
                HourlyBase hourlyBase = hourly1.get(i);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.forecast_item,forecastLayout,false);
                TextView dateText = view.findViewById(R.id.forecast_item_date_text);
                TextView infoText = view.findViewById(R.id.forecast_item_info_text);
                TextView maxText = view.findViewById(R.id.forecast_item_max_text);
                TextView minText = view.findViewById(R.id.forecast_item_min_text);

                dateText.setText(hourlyBase.getTime());
                infoText.setText(hourlyBase.getCond_txt());
                // 相对湿度
                maxText.setText(hourlyBase.getHum());
                // 降水概率
                minText.setText(hourlyBase.getPop());
                // 缓存并保存表数据
                hourlyBaseCache.setDateText(hourlyBase.getTime());
                hourlyBaseCache.setInfoText(hourlyBase.getCond_txt());
                hourlyBaseCache.setMaxText(hourlyBase.getHum());
                hourlyBaseCache.setMinText(hourlyBase.getPop());
                hourlyBaseCache.save();

                forecastLayout.addView(view);
            }

        } else {
            //在此查看返回数据失败的原因
            String status = bean.getStatus();
            Code code = Code.toEnum(status);
            Log.i("TAG", "failed code: " + code);
        }

    }

    @Override
    public void getLifestyle(Lifestyle lifestyle) {
        if(Code.OK.getCode().equalsIgnoreCase(lifestyle.getStatus())){

            // LifestyleBean 逐小时天气	List<Lifestyle>
            List<LifestyleBase> lifestyle1 = lifestyle.getLifestyle();
            LifestyleBase lifestyleBase = lifestyle1.get(0);
            Basic basic = lifestyle.getBasic();
            //生活指数详细描述
            String txt = lifestyleBase.getTxt();
            comfortText.setText(txt);
            carWashText.setText("经度:"+ basic.getLon());
            sportText.setText("纬度:"+ basic.getLat());
            //缓存数据
            weatherCache.setComfortText(txt);
            weatherCache.setCarWashText("经度:"+ basic.getLon());
            weatherCache.setSportText("纬度:"+ basic.getLat());
            weatherCache.save();

        }else {
            String status = lifestyle.getStatus();
            Code code = Code.toEnum(status);
            Log.i("TAG", "failed code: " + code);
        }


    }

    //     *********************    获取和风数据  -> **************************


    //获取并加载缓存数据
    private void getCacheAndShowData(){
        List<WeatherCache> weatherCaches = null;
        List<HourlyBaseCache>   hourlyBases   = null;
        // 获取   try 防止获取失败
        try {
            weatherCaches = LitePal.findAll(WeatherCache.class);
            hourlyBases   = LitePal.findAll(HourlyBaseCache.class);
            Log.i("获取的缓存数据",weatherCaches.toString());
            Log.i("获取的缓存数据",hourlyBases.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //加载 同样需要捕捉异常
        try {
            if(weatherCaches != null || hourlyBases != null){

                WeatherCache weatherCache = weatherCaches.get(0);

                // 将获取的缓存数据显示到界面
                toolbar.setSubtitle(weatherCache.getToolbar());
                now_updateTime.setText(weatherCache.getNow_updateTime());
                now_temText.setText(weatherCache.getNow_temText());
                now_weather.setText(weatherCache.getNow_weather());
                aqiText.setText(weatherCache.getAqiText());
                pm25Text.setText(weatherCache.getPm25Text());
                comfortText.setText(weatherCache.getComfortText());
                carWashText.setText(weatherCache.getCarWashText());
                sportText.setText(weatherCache.getSportText());

                forecastLayout.removeAllViews();
                for (int i = 0; i < 8; i++) {
                    HourlyBaseCache hourlyBaseCache = hourlyBases.get(i);

                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.forecast_item, forecastLayout, false);
                    TextView dateText = view.findViewById(R.id.forecast_item_date_text);
                    TextView infoText = view.findViewById(R.id.forecast_item_info_text);
                    TextView maxText = view.findViewById(R.id.forecast_item_max_text);
                    TextView minText = view.findViewById(R.id.forecast_item_min_text);

                    dateText.setText(hourlyBaseCache.getDateText());
                    infoText.setText(hourlyBaseCache.getInfoText());
                    maxText.setText(hourlyBaseCache.getMaxText());
                    minText.setText(hourlyBaseCache.getMinText());
                    forecastLayout.addView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 清除数据库的数据
    private void deleteAllCache(){
        try {
            LitePal.deleteAll(WeatherCache.class);
            LitePal.deleteAll(HourlyBaseCache.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 设置标题为空
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar.setTitle("");
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
        // 天气控件初始化
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
                startActivityForResult(new Intent(MainActivity.this, test_CityPicker_Activity.class),123);
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

    // 拿城市选择页面传回来的城市名  // 后续要将其保存到文件里
    // 同时设置页面里设置自己选择城市 还是通过定位决定获取哪个城市的天气
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            // 获取传回来的城市名
            //防止没有选择城市
            try {
              //  deleteAllCityNameCache();

                // 缓存选择城市
                SharedPreferences.Editor editor = getSharedPreferences(SetTing_SharedPreferencesName,MODE_PRIVATE).edit();
                editor.putString("chooseCity",data.getStringExtra("choose_city"));
                editor.apply();

                choose_cityName = data.getStringExtra("choose_city");

            //    initCityName(choose_cityName);

                Toast.makeText(MainActivity.this,"切换至选择的城市："+ choose_cityName,Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 授予或拒绝授予权限后都会回调这个方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"欢迎",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"需要获取权限才能正常使用",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

                default:
                    break;

        }
    }

    public class  MyLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            if (aMapLocation != null) {

                if (aMapLocation.getErrorCode() == 0) {

                    //获取当前位置
                    locaTion_CityName = aMapLocation.getCity();

                    // 缓存定位城市
                    SharedPreferences.Editor editor = getSharedPreferences(SetTing_SharedPreferencesName,MODE_PRIVATE).edit();
                    editor.putString("locationCity",aMapLocation.getCity());
                    editor.apply();

                } else {
                    // 动态申请定位权限 和存储卡权限，用于缓存数据
                    // 判断是否有定位权限
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED ||  ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE
                        },1);
                    }else {

                        // 一进来 ，应该调用获取位置的方法，并设置显示相关天气信息
                        Toast.makeText(MainActivity.this,"进入",Toast.LENGTH_SHORT).show();
                    }

                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }


    }

}
