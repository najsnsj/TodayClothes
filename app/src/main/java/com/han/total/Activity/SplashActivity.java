package com.han.total.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.han.total.Interface.DelayCallback;
import com.han.total.R;
import com.han.total.Util.Util;

public class SplashActivity extends AppCompatActivity {

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        GoToMainActivity(mContext);
    }

    public void GoToMainActivity(Context mContext){
        Util.getInstance(mContext).DelayCallback(1000, new DelayCallback() {
            @Override
            public void DoSomething() {
                Intent mIntent = new Intent(mContext,MainActivity.class);
                startActivity(mIntent);
            }
        });
    }
}