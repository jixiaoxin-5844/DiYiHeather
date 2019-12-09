package com.example.test4.MVPActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.test4.R;
import com.heweather.plugin.view.HeContent;
import com.heweather.plugin.view.HorizonView;
import com.heweather.plugin.view.LeftLargeView;
import com.heweather.plugin.view.RightLargeView;

public class Test_UI_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__ui_);

        LeftLargeView();


    }

    //显示左侧大布局右侧双布局
    public void LeftLargeView(){
        //左侧大布局右侧双布局控件
        LeftLargeView llView = findViewById(R.id.ll_view);

//获取左侧大布局
        LinearLayout leftLayout = llView.getLeftLayout();
//获取右上布局
        LinearLayout rightTopLayout = llView.getRightTopLayout();
//获取右下布局
        LinearLayout rightBottomLayout = llView.getRightBottomLayout();


//设置布局的背景圆角角度（单位：dp），颜色，边框宽度（单位：px），边框颜色
        llView.setStroke(5, Color.parseColor("#4bc2c5"), 1, Color.BLACK);

//添加温度描述到左侧大布局
//第一个参数为需要加入的布局
//第二个参数为文字大小，单位：sp
//第三个参数为文字颜色，默认白色
        llView.addTemp(leftLayout, 40, Color.WHITE);

//添加温度图标到右上布局，第二个参数为图标宽高（宽高1：1，单位：dp）
        llView.addWeatherIcon(rightTopLayout, 24);
//添加预警图标到右上布局
        llView.addAlarmIcon(rightTopLayout, 14);
//添加预警描述到右上布局
        llView.addAlarmTxt(rightTopLayout, 14);

//添加地址信息到右上布局
        llView.addLocation(rightTopLayout, 14, Color.WHITE);
//添加天气描述到右下布局
        llView.addCond(rightBottomLayout, 14, Color.WHITE);
//添加风向图标到右下布局
        llView.addWindIcon(rightBottomLayout, 14);
//添加风力描述到右下布局
        llView.addWind(rightBottomLayout, 14, Color.WHITE);
//添加降雨图标到右下布局
        llView.addRainIcon(rightBottomLayout, 14);
//添加降雨描述到右下布局
        llView.addRainDetail(rightBottomLayout, 14, Color.WHITE);

//设置控件的对齐方式，默认居中
        llView.setViewGravity(HeContent.GRAVITY_CENTER);
//显示布局
        llView.show();
    }

  /*  // 横向布局
    public void  horizonView(){
        //横向布局
        HorizonView horizonView = findViewById(R.id.horizon_view);
        //取消默认背景
        // horizonView.setDefaultBack(false);
        //设置布局的背景圆角角度，颜色，边框宽度，边框颜色
        horizonView.setStroke(2, Color.rgb(75,194,197),1,Color.rgb(75,194,197));
        //添加地址文字描述，第一个参数为文字大小，单位：sp ，第二个参数为文字颜色，默认白色
        horizonView.addLocation(14, Color.WHITE);

        //添加预警图标，参数为图标大小，单位：dp
        horizonView.addAlarmIcon(14);
        //添加预警文字
      //  horizonView.addAlarmTxt(14);
        //添加温度描述
        horizonView.addTemp(14, Color.WHITE);
        //添加天气图标
        horizonView.addWeatherIcon(14);
        //添加天气描述
        horizonView.addCond(14, Color.WHITE);
        //添加风向图标
      //  horizonView.addWindIcon(14);
        //添加风力描述
      //  horizonView.addWind(14, Color.WHITE);
        //添加文字：AQI
     //   horizonView.addAqiText(14, Color.WHITE);
        //添加空气质量描述
     //   horizonView.addAqiQlty(14);
        //添加空气质量数值描述
        horizonView.addAqiNum(14);
        //添加降雨图标
      //  horizonView.addRainIcon(14);
        //添加降雨描述
        horizonView.addRainDetail(14, Color.WHITE);
        //设置控件的对齐方式，默认居中
        horizonView.setViewGravity(HeContent. TYPE_VERTICAL);
        //设置控件的内边距，默认为0
        horizonView.setViewPadding(10,10,10,10);
        //显示控件
        horizonView.show();
    }*/

    //显示右侧大布局左侧横向双布局
 /*   public void RightLargeView(){
        RightLargeView rlView = findViewById(R.id.rl_view);
//方法参数同（7）左侧大布局右侧双布局
        LinearLayout rightLayout = rlView.getRightLayout();
        LinearLayout leftTopLayout = rlView.getLeftTopLayout();
        LinearLayout leftBottomLayout = rlView.getLeftBottomLayout();

//取消默认背景
        rlView.setDefaultBack(false);
        rlView.setStroke(0, Color.GRAY, 1,  Color.WHITE);
        rlView.addLocation(leftTopLayout, 14, Color.WHITE);
        rlView.addAqiText(leftTopLayout, 14);
        rlView.addAqiQlty(leftTopLayout, 14);
        rlView.addAqiNum(leftTopLayout, 14);
        rlView.addAlarmIcon(leftTopLayout, 14);
        rlView.addAlarmTxt(leftTopLayout, 14);
        rlView.addWeatherIcon(leftTopLayout, 14);

        rlView.addRainIcon(leftBottomLayout, 14);
        rlView.addRainDetail(leftBottomLayout, 14, Color.WHITE);
        rlView.addWindIcon(leftBottomLayout, 14);
        rlView.addWind(leftBottomLayout, 14, Color.WHITE);
        rlView.addCond(leftBottomLayout, 14, Color.WHITE);

        rlView.addTemp(rightLayout, 40, Color.WHITE);
        rlView.setViewGravity(HeContent.GRAVITY_RIGHT);
        rlView.show();
    }*/



}
