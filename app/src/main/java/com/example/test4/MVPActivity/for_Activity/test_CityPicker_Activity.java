package com.example.test4.MVPActivity.for_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.example.test4.MainActivity;
import com.example.test4.R;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

public class test_CityPicker_Activity extends AppCompatActivity {

    public AMapLocationClient mLocationClient_city = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__city_picker_);

        List<HotCity> hotCities = new ArrayList<>();

        hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));

        final Intent intent = new Intent(this,MainActivity.class);

        CityPicker.from(this)
                .enableAnimation(true)  //启用动画效果，默认无
                .setLocatedCity(null)  //APP自身已定位的城市，传null会自动定位（默认）
                .setHotCities(hotCities)   //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Toast.makeText(getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
                        // 向MainActivity传递 所选择的城市名
                        intent.putExtra("choose_city", data.getName());
                        setResult(123,intent);
                        finish();
                    }

                    @Override
                    public void onLocate() {
                        //定位接口，需要APP自身实现
                        //初始化定位
                        mLocationClient_city = new AMapLocationClient(getApplicationContext());
                        AMapLocationListener aMapLocationListener_city = new AMapLocationListener() {
                            @Override
                            public void onLocationChanged(AMapLocation aMapLocation) {

                                if(aMapLocation != null){
                                    CityPicker.from(test_CityPicker_Activity.this)
                                            .locateComplete(new LocatedCity(aMapLocation.getCity(),aMapLocation.getProvince(),"df"),LocateState.SUCCESS );

                                } else {
                                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                                    Log.e("AmapError","location Error, ErrCode:"
                                            + aMapLocation.getErrorCode() + ", errInfo:"
                                            + aMapLocation.getErrorInfo());
                                }
                            }
                        };
                        // 设置监听
                        mLocationClient_city.setLocationListener(aMapLocationListener_city);
                        //启动定位
                        mLocationClient_city.startLocation();
                    }
                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .show();

    }

}
