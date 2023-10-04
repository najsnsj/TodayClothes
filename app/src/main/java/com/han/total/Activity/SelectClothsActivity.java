package com.han.total.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.han.total.Fragment.donghyuktest;
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
    String weather = null;
    String date = null;
    int n = 0;
    String type = null;
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
        weather =  intent.getStringExtra("weather");
        type =  intent.getStringExtra("type");
        date = intent.getStringExtra("date");
        String con = intent.getStringExtra("condition");
        //String style = intent.getStringExtra("style");
        tv_weather.setText(weather);
        tv_type.setText(type);
        if(con.equals("캘린더")){
            Initcon(type,weather);
            iv_1.setClickable(true); iv_2.setClickable(true); iv_3.setClickable(true);
            iv_4.setClickable(true); iv_5.setClickable(true); iv_6.setClickable(true);
            iv_7.setClickable(true); iv_8.setClickable(true); iv_9.setClickable(true);
            tv_weather.setClickable(true);
        }else {
            iv_1.setClickable(false); iv_2.setClickable(false); iv_3.setClickable(false);
            iv_4.setClickable(false); iv_5.setClickable(false); iv_6.setClickable(false);
            iv_7.setClickable(false); iv_8.setClickable(false); iv_9.setClickable(false);
            tv_weather.setClickable(false);
            Init(type, weather);
        }

    }

    @OnClick({R.id.tv_weather}) void ClickW(){
        if(weather.equals("봄")){
            weather = "여름";
            tv_weather.setText(weather);
            Initcon(type,weather);
        }else if(weather.equals("여름")){
            weather = "겨울";
            tv_weather.setText(weather);
            Initcon(type,weather);
        }else{
            weather = "봄";
            tv_weather.setText(weather);
            Initcon(type,weather);
        }
    }
    @OnClick({R.id.iv_1,R.id.iv_2,R.id.iv_3,R.id.iv_4,R.id.iv_5,R.id.iv_6,R.id.iv_7,R.id.iv_8,R.id.iv_9}) void Click2(View view){
        switch(view.getId()){
            case R.id.iv_1:
                n=1;
                break;
            case R.id.iv_2:
                n=2;
                break;
            case R.id.iv_3:
                n=3;
                break;
            case R.id.iv_4:
                n=4;
                break;
            case R.id.iv_5:
                n=5;
                break;
            case R.id.iv_6:
                n=6;
                break;
            case R.id.iv_7:
                n=7;
                break;
            case R.id.iv_8:
                n=8;
                break;
            case R.id.iv_9:
                n=9;
                break;
        }
        Intent intent = new Intent(SelectClothsActivity.this, donghyuktest.class);
        data.getInstance(mContext).setCalC(date,type,type+weather+n+".jpg");
        //intent.putExtra("image",type+weather+n+".jpg");
        //intent.putExtra("type",type);
        startActivity(intent);
        finish();
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

    void Init(String type, String weather) {
        if (weather.equals("봄")) {
            if (type.equals("아우터")) {
                iv_1.setImageResource(0);
                iv_2.setImageResource(0);
                iv_3.setImageResource(0);
                iv_4.setImageResource(0);
                iv_5.setImageResource(0);
                iv_6.setImageResource(0);
                iv_7.setImageResource(0);
                iv_8.setImageResource(0);
                iv_9.setImageResource(0);
                for (int i = 1; i <= data.getInstance(mContext).getNumber(type + weather); i++) {
                    String name = data.getInstance(mContext).getPicture(type + weather, i);
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name, i);
                }
            } else if (type.equals("상의")) {
                iv_1.setImageResource(0);
                iv_2.setImageResource(0);
                iv_3.setImageResource(0);
                iv_4.setImageResource(0);
                iv_5.setImageResource(0);
                iv_6.setImageResource(0);
                iv_7.setImageResource(0);
                iv_8.setImageResource(0);
                iv_9.setImageResource(0);
                for (int i = 1; i <= data.getInstance(mContext).getNumber(type + weather); i++) {
                    String name = data.getInstance(mContext).getPicture(type + weather, i);
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name, i);
                }
            } else if (type.equals("하의")) {
                iv_1.setImageResource(0);
                iv_2.setImageResource(0);
                iv_3.setImageResource(0);
                iv_4.setImageResource(0);
                iv_5.setImageResource(0);
                iv_6.setImageResource(0);
                iv_7.setImageResource(0);
                iv_8.setImageResource(0);
                iv_9.setImageResource(0);
                for (int i = 1; i <= data.getInstance(mContext).getNumber(type + weather); i++) {
                    String name = data.getInstance(mContext).getPicture(type + weather, i);
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name, i);
                }
            }
        } else if (weather.equals("여름")) {
            if (type.equals("아우터")) {
                iv_1.setImageResource(0);
                iv_2.setImageResource(0);
                iv_3.setImageResource(0);
                iv_4.setImageResource(0);
                iv_5.setImageResource(0);
                iv_6.setImageResource(0);
                iv_7.setImageResource(0);
                iv_8.setImageResource(0);
                iv_9.setImageResource(0);
                for (int i = 1; i <= data.getInstance(mContext).getNumber(type + weather); i++) {
                    String name = data.getInstance(mContext).getPicture(type + weather, i);
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name, i);
                }
            } else if (type.equals("상의")) {
                iv_1.setImageResource(0);
                iv_2.setImageResource(0);
                iv_3.setImageResource(0);
                iv_4.setImageResource(0);
                iv_5.setImageResource(0);
                iv_6.setImageResource(0);
                iv_7.setImageResource(0);
                iv_8.setImageResource(0);
                iv_9.setImageResource(0);
                for (int i = 1; i <= data.getInstance(mContext).getNumber(type + weather); i++) {
                    String name = data.getInstance(mContext).getPicture(type + weather, i);
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name, i);
                }
            } else if (type.equals("하의")) {
                iv_1.setImageResource(0);
                iv_2.setImageResource(0);
                iv_3.setImageResource(0);
                iv_4.setImageResource(0);
                iv_5.setImageResource(0);
                iv_6.setImageResource(0);
                iv_7.setImageResource(0);
                iv_8.setImageResource(0);
                iv_9.setImageResource(0);
                for (int i = 1; i <= data.getInstance(mContext).getNumber(type + weather); i++) {
                    String name = data.getInstance(mContext).getPicture(type + weather, i);
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name, i);
                }
            }
        } else if (weather.equals("겨울")) {
            if (type.equals("아우터")) {
                iv_1.setImageResource(0);
                iv_2.setImageResource(0);
                iv_3.setImageResource(0);
                iv_4.setImageResource(0);
                iv_5.setImageResource(0);
                iv_6.setImageResource(0);
                iv_7.setImageResource(0);
                iv_8.setImageResource(0);
                iv_9.setImageResource(0);
                for (int i = 1; i <= data.getInstance(mContext).getNumber(type + weather); i++) {
                    String name = data.getInstance(mContext).getPicture(type + weather, i);
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name, i);
                }
            } else if (type.equals("상의")) {
                iv_1.setImageResource(0);
                iv_2.setImageResource(0);
                iv_3.setImageResource(0);
                iv_4.setImageResource(0);
                iv_5.setImageResource(0);
                iv_6.setImageResource(0);
                iv_7.setImageResource(0);
                iv_8.setImageResource(0);
                iv_9.setImageResource(0);
                for (int i = 1; i <= data.getInstance(mContext).getNumber(type + weather); i++) {
                    String name = data.getInstance(mContext).getPicture(type + weather, i);
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name, i);
                }
            } else if (type.equals("하의")) {
                iv_1.setImageResource(0);
                iv_2.setImageResource(0);
                iv_3.setImageResource(0);
                iv_4.setImageResource(0);
                iv_5.setImageResource(0);
                iv_6.setImageResource(0);
                iv_7.setImageResource(0);
                iv_8.setImageResource(0);
                iv_9.setImageResource(0);
                for (int i = 1; i <= data.getInstance(mContext).getNumber(type + weather); i++) {
                    String name = data.getInstance(mContext).getPicture(type + weather, i);
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name, i);
                }
            }
        }
        Logg.e(Global.USER_HTJ, "타입: " + type);
        Logg.e(Global.USER_HTJ, "날씨: " + weather);
        Logg.e(Global.USER_HTJ, "개수: " + data.getInstance(mContext).getNumber(type + weather));
    }
        void Initcon(String type , String weather){
            if(weather.equals("봄")){
                if(type.equals("아우터")){
                    iv_1.setImageResource(0);iv_2.setImageResource(0);iv_3.setImageResource(0);iv_4.setImageResource(0);iv_5.setImageResource(0);iv_6.setImageResource(0);iv_7.setImageResource(0);iv_8.setImageResource(0);iv_9.setImageResource(0);
                    for(int i=1;i<=data.getInstance(mContext).getNumber(type+weather);i++){
                        String name = data.getInstance(mContext).getPicture(type+weather,i);
                        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,i);
                    }
                }else if(type.equals("상의")){
                    iv_1.setImageResource(0);iv_2.setImageResource(0);iv_3.setImageResource(0);iv_4.setImageResource(0);iv_5.setImageResource(0);iv_6.setImageResource(0);iv_7.setImageResource(0);iv_8.setImageResource(0);iv_9.setImageResource(0);
                    for(int i=1;i<=data.getInstance(mContext).getNumber(type+weather);i++){
                        String name = data.getInstance(mContext).getPicture(type+weather,i);
                        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,i);
                    }
                }else if(type.equals("하의")){
                    iv_1.setImageResource(0);iv_2.setImageResource(0);iv_3.setImageResource(0);iv_4.setImageResource(0);iv_5.setImageResource(0);iv_6.setImageResource(0);iv_7.setImageResource(0);iv_8.setImageResource(0);iv_9.setImageResource(0);
                    for(int i=1;i<=data.getInstance(mContext).getNumber(type+weather);i++){
                        String name = data.getInstance(mContext).getPicture(type+weather,i);
                        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,i);
                    }
                }
            }else if(weather.equals("여름")){
                if(type.equals("아우터")){
                    iv_1.setImageResource(0);iv_2.setImageResource(0);iv_3.setImageResource(0);iv_4.setImageResource(0);iv_5.setImageResource(0);iv_6.setImageResource(0);iv_7.setImageResource(0);iv_8.setImageResource(0);iv_9.setImageResource(0);
                    for(int i=1;i<=data.getInstance(mContext).getNumber(type+weather);i++){
                        String name = data.getInstance(mContext).getPicture(type+weather,i);
                        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,i);
                    }
                }else if(type.equals("상의")){
                    iv_1.setImageResource(0);iv_2.setImageResource(0);iv_3.setImageResource(0);iv_4.setImageResource(0);iv_5.setImageResource(0);iv_6.setImageResource(0);iv_7.setImageResource(0);iv_8.setImageResource(0);iv_9.setImageResource(0);
                    for(int i=1;i<=data.getInstance(mContext).getNumber(type+weather);i++){
                        String name = data.getInstance(mContext).getPicture(type+weather,i);
                        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,i);
                    }
                }else if(type.equals("하의")){
                    iv_1.setImageResource(0);iv_2.setImageResource(0);iv_3.setImageResource(0);iv_4.setImageResource(0);iv_5.setImageResource(0);iv_6.setImageResource(0);iv_7.setImageResource(0);iv_8.setImageResource(0);iv_9.setImageResource(0);
                    for(int i=1;i<=data.getInstance(mContext).getNumber(type+weather);i++){
                        String name = data.getInstance(mContext).getPicture(type+weather,i);
                        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,i);
                    }
                }
            }else if(weather.equals("겨울")){
                if(type.equals("아우터")){
                    iv_1.setImageResource(0);iv_2.setImageResource(0);iv_3.setImageResource(0);iv_4.setImageResource(0);iv_5.setImageResource(0);iv_6.setImageResource(0);iv_7.setImageResource(0);iv_8.setImageResource(0);iv_9.setImageResource(0);
                    for(int i=1;i<=data.getInstance(mContext).getNumber(type+weather);i++){
                        String name = data.getInstance(mContext).getPicture(type+weather,i);
                        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,i);
                    }
                }else if(type.equals("상의")){
                    iv_1.setImageResource(0);iv_2.setImageResource(0);iv_3.setImageResource(0);iv_4.setImageResource(0);iv_5.setImageResource(0);iv_6.setImageResource(0);iv_7.setImageResource(0);iv_8.setImageResource(0);iv_9.setImageResource(0);
                    for(int i=1;i<=data.getInstance(mContext).getNumber(type+weather);i++){
                        String name = data.getInstance(mContext).getPicture(type+weather,i);
                        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,i);
                    }
                }else if(type.equals("하의")){
                    iv_1.setImageResource(0);iv_2.setImageResource(0);iv_3.setImageResource(0);iv_4.setImageResource(0);iv_5.setImageResource(0);iv_6.setImageResource(0);iv_7.setImageResource(0);iv_8.setImageResource(0);iv_9.setImageResource(0);
                    for(int i=1;i<=data.getInstance(mContext).getNumber(type+weather);i++){
                        String name = data.getInstance(mContext).getPicture(type+weather,i);
                        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,i);
                    }
                }
            }

        Logg.e(Global.USER_HTJ,"타입: "+type);
        Logg.e(Global.USER_HTJ,"날씨: "+weather);
        Logg.e(Global.USER_HTJ,"개수: "+data.getInstance(mContext).getNumber(type+weather));

       /* if(data.getInstance(mContext).getNumber(type+weather)==0){
            iv_8.setVisibility(View.GONE);
            iv_9.setVisibility(View.GONE);
        }else if(data.getInstance(mContext).getNumber(type+weather)==1){
            String name = data.getInstance(mContext).getPicture(type+weather,1);
            loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,true);
            iv_9.setVisibility(View.GONE);
        }else{
            String name = data.getInstance(mContext).getPicture(type+weather,1);
            Logg.e(Global.USER_HTJ,"아우터 겨울1: "+name);
            String name2 = data.getInstance(mContext).getStyle(type+weather,1);
            tv_weather.setText(name2);
            loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,true);
            name = data.getInstance(mContext).getPicture(type+weather,2);
            Logg.e(Global.USER_HTJ,"아우터 겨울2: "+name);
            loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,false);
        }*/
    }
    private void loadImageFromStorage(String path, String name,int i) {
        try {
            File f;
            f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            switch (i) {
                case 1:
                    iv_1.setImageBitmap(b);
                    break;
                case 2:
                    iv_2.setImageBitmap(b);
                    break;
                case 3:
                    iv_3.setImageBitmap(b);
                    break;
                case 4:
                    iv_4.setImageBitmap(b);
                    break;
                case 5:
                    iv_5.setImageBitmap(b);
                    break;
                case 6:
                    iv_6.setImageBitmap(b);
                    break;
                case 7:
                    iv_7.setImageBitmap(b);
                    break;
                case 8:
                    iv_8.setImageBitmap(b);
                    break;
                case 9:
                    iv_9.setImageBitmap(b);
                    break;
            }
        } catch (FileNotFoundException e) {
            Log.e("HAN", "exception: " + e);
            e.printStackTrace();
        }
    }
    /*private void loadImageFromStorage(String path, String name,boolean flag)
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
    }*/
}