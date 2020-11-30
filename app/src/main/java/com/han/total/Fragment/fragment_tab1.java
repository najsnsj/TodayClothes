package com.han.total.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.han.total.Adapter.TemplateAdapter;
import com.han.total.R;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;
import com.han.total.Util.MediaScanning;
import com.han.total.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class fragment_tab1 extends Fragment{



    private Context mContext;
    private static final int CAMEARA_REQUEST_CODE = 101;
    @BindView(R.id.ll_weather)
    LinearLayout ll_weather;
    @BindView(R.id.ll_type)
    LinearLayout ll_type;

    @BindView(R.id.tv_title_type)
    TextView tv_title_type;
    @BindView(R.id.tv_title_weather)
    TextView tv_title_weather;

    Bitmap bitmap;
    String weather="봄",type="아우터";

    public fragment_tab1(Context context) {
        mContext = context;
        // Required empty public constructor
    }

    public fragment_tab1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // 카메라 띄우는 함수
    @OnClick(R.id.iv_camera) void ClickCamera(){
        Camera();
    }
    // 카메라 띄우는 함수
    void Camera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    //클릭리스너 ,
    @OnClick({R.id.ll_click_type,R.id.ll_click_weather,R.id.ll_winter0}) void Click(View v){
        if(v.getId()==R.id.ll_click_type){
            Clear();
            ll_type.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.ll_click_weather){
            Clear();
            ll_weather.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.ll_winter0){
            Toast.makeText(mContext,"사진이 저장되었습니다.",Toast.LENGTH_SHORT).show();
            Save(type,weather);
        }
    }

    // 아우터, 상의 , 하의 리스트
    @OnClick({R.id.tv_outer,R.id.tv_top,R.id.tv_bottom,R.id.tv_spring,R.id.tv_summer,R.id.tv_winter}) void Click1(View v){
        if(v.getId()==R.id.tv_outer){
            Clear();
            tv_title_type.setText("아우터");
            type="아우터";
        }else if(v.getId()==R.id.tv_top){
            Clear();
            type="상의";
            tv_title_type.setText("상의");
        }else if(v.getId()==R.id.tv_bottom){
            Clear();
            type="하의";
            tv_title_type.setText("하의");
        }else if(v.getId()==R.id.tv_spring){
            Clear();
            weather="봄";
            tv_title_weather.setText("봄 / 가을");
        }else if(v.getId()==R.id.tv_summer){
            Clear();
            weather="여름";
            tv_title_weather.setText("여름");
        }else if(v.getId()==R.id.tv_winter){
            Clear();
            weather="겨울";
            tv_title_weather.setText("겨울");
        }
    }

    void Clear(){
        ll_type.setVisibility(View.GONE);
        ll_weather.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case CAMEARA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data.hasExtra("data")) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null) {
                        //iv_0.setImageBitmap(bitmap);
                    }

                }
                break;
        }
    }

    void Save(String type, String weather){
        String path="";
        String name="";
        Logg.e(Global.USER_HTJ,"저장 타입: "+type);
        Logg.e(Global.USER_HTJ,"저장 날씨: "+weather);
        Logg.e(Global.USER_HTJ,"data.getInstance(mContext).setNumber(2,type+weather);: "+data.getInstance(mContext).getNumber(type+weather));


        if(data.getInstance(mContext).getNumber(type+weather)==0){
            data.getInstance(mContext).setNumber(1,type+weather);
            name = type+weather+"1.jpg";
            data.getInstance(mContext).setPicture(name,type+weather,1);
            path = createPictureFilePath(name);
            Logg.e(Global.USER_HTJ,"저장 name: "+1);
            Logg.e(Global.USER_HTJ,"저장 name: "+name);
            Logg.e(Global.USER_HTJ,"저장 path: "+path);

        }else if(data.getInstance(mContext).getNumber(type+weather)==1){
            data.getInstance(mContext).setNumber(2,type+weather);
            name = type+weather+"2.jpg";
            data.getInstance(mContext).setPicture(name,type+weather,2);
            Logg.e(Global.USER_HTJ,"data.getInstance(mContext).setPicture(name,type+weather,2): "+data.getInstance(mContext).getPicture(type+weather,2));

            path = createPictureFilePath(name);
            Logg.e(Global.USER_HTJ,"저장 name: "+2);
            Logg.e(Global.USER_HTJ,"저장 name: "+name);
            Logg.e(Global.USER_HTJ,"저장 path: "+path);
        }else{


//            data.getInstance(mContext).setNumber(2,type+weather);
//            name = type+weather+"2.jpg";
//            data.getInstance(mContext).setPicture(name,type+weather,2);
//            path = createPictureFilePath(name);
//            Logg.e("저장 name: "+3);
//            Logg.e("저장 name: "+name);
//            Logg.e("저장 path: "+path);


            data.getInstance(mContext).setNumber(data.getInstance(mContext).getNumber(type+weather)+1,type+weather);
            if(data.getInstance(mContext).getNumber(type+weather)%2==0){
                name = type+weather+"1.jpg";
                data.getInstance(mContext).setPicture(name,type+weather,1);
                path = createPictureFilePath(name);
                Logg.e("저장 name: "+name);
                Logg.e("저장 path: "+path);
            }else{
                name = type+weather+"2.jpg";
                data.getInstance(mContext).setPicture(name,type+weather,2);
                path = createPictureFilePath(name);
                Logg.e("저장 name: "+name);
                Logg.e("저장 path: "+path);
            }

            Logg.e("저장 name: "+3);
            Logg.e("저장 name: "+name);
            Logg.e("저장 path: "+path);
        }
        Logg.e("getPicture: "+ data.getInstance(mContext).getPicture(type+weather,2));




        Logg.e(Global.USER_HTJ,"path:"+path);
        saveBitmapAsFile(bitmap,path);
        scanMediaFile(new File(path));
    }

    // 카메라로 찍은 함수를 특정 위치로 저장하는 함수
    private void saveBitmapAsFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);
        OutputStream os = null;

        try {
            file.createNewFile();
            os = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
            Toast.makeText(mContext,"사진 저장",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("HAN","Exception: "+e);
            e.printStackTrace();
        }
    }

    // 미디어 스캔으로 업데이트
    private void scanMediaFile(final File file) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new MediaScanning(mContext, file);
            }
        }, 900);
    }

    public static final String SAVE_MEDIA_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/";
    public String createPictureFilePath(String fileName) {
        createMediaFileDirectory();
        //String fileName = "img1"+ ".jpg";
        String fullPath = SAVE_MEDIA_PATH + fileName;
        return fullPath;
    }

    // 특정 미디어 파일의 디렉토리를 생성하는 함수
    private void createMediaFileDirectory() {
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }

        File cameraDir = new File(SAVE_MEDIA_PATH);
        if (!cameraDir.exists()) {
            cameraDir.mkdir();
        }
    }




}