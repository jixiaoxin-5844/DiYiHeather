package com.example.test4.MVPActivity.for_Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("BaseActivity", getClass().getSimpleName());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
