package com.example.test4.MVPModel;

import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.example.test4.MVPActivity.for_Activity.MyApplication;

/*
 *   通过高德地图 SDK
 *   获取当前所处 城市
 * */
public class GetLocation {

   /* // 存放获取到的城市
    String strCity;

    //定位需要的声明
    private AMapLocationClient mLocationClient = null; //定位发起端
    private AMapLocationClientOption mLocationOption = null;  //定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;


    //获取当前所处城市
    public String get_city(){

        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.getContext());

        mLocationClient.setLocationListener(new MyLocationListener());

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

        return strCity;

    }


    public class MyLocationListener implements AMapLocationListener{

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            if(aMapLocation != null){

                StringBuilder currentPosition = new StringBuilder();
                currentPosition.append("市: ").append(aMapLocation.getCity()).append("\n");

                strCity = currentPosition+"";
            }

            else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }

        }
    }*/



}
