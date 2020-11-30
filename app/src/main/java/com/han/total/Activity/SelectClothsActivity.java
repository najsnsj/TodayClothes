package com.han.total.Activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.han.total.R;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;
import com.han.total.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SelectClothsActivity extends AppCompatActivity {

    Context mContext;
    @BindView(R.id.tv_weather)
    TextView tv_weather;
    @BindView(R.id.tv_type)
    TextView tv_type;

    @BindView(R.id.iv_1) ImageView iv_1;
    @BindView(R.id.iv_2) ImageView iv_2;
    @BindView(R.id.iv_3) ImageView iv_3;
    @BindView(R.id.iv_4) ImageView iv_4;
    @BindView(R.id.iv_5) ImageView iv_5;
    @BindView(R.id.iv_6) ImageView iv_6;
    @BindView(R.id.iv_7) ImageView iv_7;
    @BindView(R.id.iv_8) ImageView iv_8;
    @BindView(R.id.iv_9) ImageView iv_9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cloths);
        mContext = this;
        ButterKnife.bind(this);

        // 인텐트 파라미터 받는 과정
        Intent intent = getIntent();
        String weather =  intent.getStringExtra("weather");
        String type =  intent.getStringExtra("type");

        tv_weather.setText(weather);
        tv_type.setText(type);
        Init(type,weather);
    }

    // 이메일 띄우기
    @OnClick({R.id.fl_fragment1}) void Click(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("plain/text");
        String[] address = {"email@address.com"};
        email.putExtra(Intent.EXTRA_EMAIL, address);
        email.putExtra(Intent.EXTRA_SUBJECT, "test@test");
        email.putExtra(Intent.EXTRA_TEXT, "내용 미리보기 (미리적을 수 있음)");
        startActivity(email);
    }

    void Init(String type , String weather){
        if(weather.equals("봄")){
            if(type.equals("아우터")){
                iv_1.setImageResource(R.drawable.springouter1);
                iv_2.setImageResource(R.drawable.springouter2);
                iv_3.setImageResource(R.drawable.springouter3);
                iv_4.setImageResource(R.drawable.springouter4);
                iv_5.setImageResource(R.drawable.springouter5);
                iv_6.setImageResource(R.drawable.springouter6);
                iv_7.setImageResource(R.drawable.springouter7);
            }else if(type.equals("상의")){
                iv_1.setImageResource(R.drawable.springtop1);
                iv_2.setImageResource(R.drawable.springtop2);
                iv_3.setImageResource(R.drawable.springtop3);
                iv_4.setImageResource(R.drawable.springtop4);
                iv_5.setImageResource(R.drawable.springtop5);
                iv_6.setImageResource(R.drawable.springtop6);
                iv_7.setImageResource(R.drawable.springtop7);
            }else if(type.equals("하의")){
                iv_1.setImageResource(R.drawable.springbottom1);
                iv_2.setImageResource(R.drawable.springbottom2);
                iv_3.setImageResource(R.drawable.springbottom3);
                iv_4.setImageResource(R.drawable.springbottom4);
                iv_5.setImageResource(R.drawable.springbottom5);
                iv_6.setImageResource(R.drawable.springbottom6);
                iv_7.setImageResource(R.drawable.springbottom7);
            }
        }else if(weather.equals("여름")){
            if(type.equals("아우터")){
                iv_1.setImageResource(R.drawable.summerouter1);
                iv_2.setImageResource(R.drawable.summerouter2);
                iv_3.setImageResource(R.drawable.summerouter3);
                iv_4.setImageResource(R.drawable.summerouter4);
                iv_5.setImageResource(R.drawable.summerouter5);
                iv_6.setImageResource(R.drawable.summerouter6);
                iv_7.setImageResource(R.drawable.summerouter7);
            }else if(type.equals("상의")){
                iv_1.setImageResource(R.drawable.summertop1);
                iv_2.setImageResource(R.drawable.summertop2);
                iv_3.setImageResource(R.drawable.summertop3);
                iv_4.setImageResource(R.drawable.summertop4);
                iv_5.setImageResource(R.drawable.summertop5);
                iv_6.setImageResource(R.drawable.summertop6);
                iv_7.setImageResource(R.drawable.summertop7);

            }else if(type.equals("하의")){
                iv_1.setImageResource(R.drawable.summerbottom1);
                iv_2.setImageResource(R.drawable.summerbottom2);
                iv_3.setImageResource(R.drawable.summerbottom3);
                iv_4.setImageResource(R.drawable.summerbottom4);
                iv_5.setImageResource(R.drawable.summerbottom5);
                iv_6.setImageResource(R.drawable.summerbottom6);
                iv_7.setImageResource(R.drawable.summerbottom7);
            }
        }else if(weather.equals("겨울")){
            if(type.equals("아우터")){
                iv_1.setImageResource(R.drawable.winterouter1);
                iv_2.setImageResource(R.drawable.winterouter2);
                iv_3.setImageResource(R.drawable.winterouter3);
                iv_4.setImageResource(R.drawable.winterouter4);
                iv_5.setImageResource(R.drawable.winterouter5);
                iv_6.setImageResource(R.drawable.winterouter6);
                iv_7.setImageResource(R.drawable.winterouter7);
                Logg.e(Global.USER_HTJ,"아우터 겨울: "+data.getInstance(mContext).getNumber(type+weather));
            }else if(type.equals("상의")){
                iv_1.setImageResource(R.drawable.wintertop1);
                iv_2.setImageResource(R.drawable.wintertop2);
                iv_3.setImageResource(R.drawable.wintertop3);
                iv_4.setImageResource(R.drawable.wintertop4);
                iv_5.setImageResource(R.drawable.wintertop5);
                iv_6.setImageResource(R.drawable.wintertop6);
                iv_7.setImageResource(R.drawable.wintertop7);
            }else if(type.equals("하의")){
                iv_1.setImageResource(R.drawable.winterbottom1);
                iv_2.setImageResource(R.drawable.winterbottom2);
                iv_3.setImageResource(R.drawable.winterbottom3);
                iv_4.setImageResource(R.drawable.winterbottom4);
                iv_5.setImageResource(R.drawable.winterbottom5);
                iv_6.setImageResource(R.drawable.winterbottom6);
                iv_7.setImageResource(R.drawable.winterbottom7);
            }
        }

        Logg.e(Global.USER_HTJ,"타입: "+type);
        Logg.e(Global.USER_HTJ,"날씨: "+weather);
        Logg.e(Global.USER_HTJ,"개수: "+data.getInstance(mContext).getNumber(type+weather));

        if(data.getInstance(mContext).getNumber(type+weather)==0){
            iv_8.setVisibility(View.GONE);
            iv_9.setVisibility(View.GONE);
        }else if(data.getInstance(mContext).getNumber(type+weather)==1){
            String name = data.getInstance(mContext).getPicture(type+weather,1);
            loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,true);
            iv_9.setVisibility(View.GONE);
        }else{
            String name = data.getInstance(mContext).getPicture(type+weather,1);
            Logg.e(Global.USER_HTJ,"아우터 겨울1: "+name);
            loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,true);
            name = data.getInstance(mContext).getPicture(type+weather,2);
            Logg.e(Global.USER_HTJ,"아우터 겨울2: "+name);
            loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,false);
        }
    }


    private void loadImageFromStorage(String path, String name,boolean flag)
    {
        try {
            File f;
            f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            if (flag){
                iv_8.setImageBitmap(b);
            }else{
                iv_9.setImageBitmap(b);
            }

        }
        catch (FileNotFoundException e)
        {
            Log.e("HAN","exception: "+e);
            e.printStackTrace();
        }
    }


}