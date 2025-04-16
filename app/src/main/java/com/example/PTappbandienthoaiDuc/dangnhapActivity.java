package com.example.PTappbandienthoaiDuc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.PTappbandienthoaiDuc.activity.DangNhapActivity;

public class dangnhapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_mhcactivity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(dangnhapActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        },1);
    }
}