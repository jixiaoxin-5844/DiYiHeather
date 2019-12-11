package com.example.test4.MVPActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.example.test4.MainActivity;
import com.example.test4.R;

public class setTingActivity extends AppCompatActivity {

    Toolbar setting_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ting);

        setting_toolbar = findViewById(R.id.setting_toolbar);
        setSupportActionBar(setting_toolbar);

        //设置返回按钮
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        // 设置页面标题
     //   setTitle("设置");


    }

    // 初始化 toolbar  显示返回按钮 及功能

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            // 点击返回按钮  返回主界面
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return true;
    }




}
