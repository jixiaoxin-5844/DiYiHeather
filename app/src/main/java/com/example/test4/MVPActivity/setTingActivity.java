package com.example.test4.MVPActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.test4.MainActivity;
import com.example.test4.R;

public class setTingActivity extends AppCompatActivity {

    Toolbar setting_toolbar;
    Switch aSwitch;  //是否使用定位
    int i = 0; // aSwitch状态  单击关闭后变为1
    private final String SetTing_SharedPreferencesName = "DiYi_My_SetTing" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ting);

        initView();
        setSupportActionBar(setting_toolbar);
        //设置返回按钮
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        //初始化设置页面
        initSetTing();


        //设置按钮 aSwitch 监听
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences(SetTing_SharedPreferencesName,MODE_PRIVATE).edit();
                ++i;

                //1 表示关闭使用定位城市
                if(i %2 == 1){

                    editor.putInt("if_Choose_City",1);
                    editor.apply();
                    Toast.makeText(getApplication(),"使用选择城市",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    editor.putInt("if_Choose_City",0);
                    editor.apply();
                }

                Toast.makeText(getApplication(),"使用定位城市",Toast.LENGTH_SHORT).show();


            }
        });


    }

    // 初始化设置信息
    private void initSetTing(){
        // 获取设置信息
        SharedPreferences pre = getSharedPreferences(SetTing_SharedPreferencesName,MODE_PRIVATE);

        i = pre.getInt("if_Choose_City",0);  // 是否启用定位城市

        // 初始化设置页面
        if(i %2 == 1){
            aSwitch.setChecked(false);
        }


    }

    private void initView(){
        setting_toolbar = findViewById(R.id.setting_toolbar);
        aSwitch         = findViewById(R.id.setTing_switch1);
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
