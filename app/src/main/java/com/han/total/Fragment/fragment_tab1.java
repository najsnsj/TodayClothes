package com.han.total.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.han.total.Activity.MainActivity;
import com.han.total.Classifier.ClassifierWithModel;
import com.han.total.R;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;
import com.han.total.Util.MediaScanning;
import com.han.total.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class fragment_tab1 extends Fragment{

    private Context mContext;
    private static final int CAMEARA_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;
    private com.han.total.Classifier.ClassifierWithModel cls;
    @BindView(R.id.ll_weather)
    LinearLayout ll_weather;
    @BindView(R.id.ll_type)
    LinearLayout ll_type;
    @BindView(R.id.ll_style)
    LinearLayout ll_style;
    @BindView(R.id.tv_title_type)
    TextView tv_title_type;
    @BindView(R.id.tv_title_weather)
    TextView tv_title_weather;
    @BindView(R.id.tv_title_style)
    TextView tv_title_style;
    @BindView(R.id.iv_camera)
    ImageView iv_camera;


    Bitmap bitmap;
    String weather="봄",type="아우터",style="스포츠";

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
        cls = new ClassifierWithModel(mContext.getApplicationContext());
        try {
            cls.init();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
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
    @OnClick(R.id.camera_btn) void ClickCamera(){Camera();}
    @OnClick(R.id.gallery_btn) void ClickGallery(){Gallery();}

    void Gallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 102);
    }

    void Camera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    //클릭리스너 ,
    @OnClick({R.id.ll_click_type,R.id.ll_click_weather,R.id.ll_winter0,R.id.ll_click_style}) void Click(View v){
        if(v.getId()==R.id.ll_click_type){
            Clear();
            ll_type.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.ll_click_weather) {
            Clear();
            ll_weather.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.ll_click_style){
            Clear();
            ll_style.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.ll_winter0){
            if(tv_title_type.getText().equals("계절")||tv_title_weather.getText().equals("종류")||tv_title_style.getText().equals("스타일")){
                Toast.makeText(mContext, "타일을 모두 선택해주세요.", Toast.LENGTH_SHORT).show();
            }else {
                Save(type, weather, style);
            }
        }
    }

    // 아우터, 상의 , 하의 리스트
    @OnClick({R.id.tv_outer,R.id.tv_top,R.id.tv_bottom,R.id.tv_spring,R.id.tv_summer,R.id.tv_winter,R.id.tv_classic,R.id.tv_sport,R.id.tv_casual}) void Click1(View v){
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
        }else if(v.getId()==R.id.tv_sport){
            Clear();
            style="스포츠";
            tv_title_style.setText("스포츠");
        }else if(v.getId()==R.id.tv_casual){
            Clear();
            style="캐주얼";
            tv_title_style.setText("캐주얼");
        }else if(v.getId()==R.id.tv_classic){
            Clear();
            style="클래식";
            tv_title_style.setText("클래식");
        }
    }

    void Clear(){
        ll_type.setVisibility(View.GONE);
        ll_weather.setVisibility(View.GONE);
        ll_style.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case CAMEARA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data.hasExtra("data")) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    iv_camera.setImageBitmap(bitmap);
                    if (bitmap != null) {
                        //iv_0.setImageBitmap(bitmap);
                        Pair<String, Float> output = cls.classify(bitmap);
                        String resultStr = String.format(Locale.ENGLISH,
                                "class : %s, prob : %.2f%%",
                                output.first, output.second * 100);
                        if(isOutterGarment(output.first)!="없음"){
                            resultStr = isOutterGarment(output.first);
                        }else if(isUpperGarment(output.first)!="없음") {
                            resultStr = isUpperGarment(output.first);
                        }else if(isLowerGarment(output.first)!="없음") {
                            resultStr = isLowerGarment(output.first);
                        }else{
                            tv_title_type.setText("아우터");
                            type="아우터";
                            tv_title_weather.setText("봄 / 가을");
                            weather="봄";
                        }

                        if(resultStr.contains("봄")){
                            tv_title_type.setText(resultStr.replace("봄",""));
                            type=resultStr.replace("봄","");
                            tv_title_weather.setText("봄 / 가을");
                            weather="봄";
                        }else if(resultStr.contains("여름")){
                            tv_title_type.setText(resultStr.replace("여름",""));
                            type=resultStr.replace("여름","");
                            tv_title_weather.setText("여름");
                            weather="여름";
                        }else if(resultStr.contains("겨울")){
                            tv_title_type.setText(resultStr.replace("겨울",""));
                            type=resultStr.replace("겨울","");
                            tv_title_weather.setText("겨울");
                            weather="겨울";
                        }else{
                            tv_title_type.setText("아우터");
                            type="아우터";
                            tv_title_weather.setText("봄 / 가을");
                            weather="봄";
                        }
                        //imageView.setImageBitmap(bitmap);
                    }

                }
                break;
            case GALLERY_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Uri imageUri = data.getData();
                    if (imageUri != null) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                            iv_camera.setImageBitmap(bitmap);
                            Pair<String, Float> output = cls.classify(bitmap);
                            String resultStr = String.format(Locale.ENGLISH,
                                    "class : %s, prob : %.2f%%",
                                    output.first, output.second * 100);
                            if(isOutterGarment(output.first)!="없음"){
                                resultStr = isOutterGarment(output.first);
                            }else if(isUpperGarment(output.first)!="없음") {
                                resultStr = isUpperGarment(output.first);
                            }else if(isLowerGarment(output.first)!="없음") {
                                resultStr = isLowerGarment(output.first);
                            }else{
                                tv_title_type.setText("아우터");
                                type="아우터";
                                tv_title_weather.setText("봄 / 가을");
                                weather="봄";
                            }
                            if(resultStr.contains("봄")){
                                tv_title_type.setText(resultStr.replace("봄",""));
                                type=resultStr.replace("봄","");
                                tv_title_weather.setText("봄 / 가을");
                                weather="봄";
                            }else if(resultStr.contains("여름")){
                                tv_title_type.setText(resultStr.replace("여름",""));
                                type=resultStr.replace("여름","");
                                tv_title_weather.setText("여름");
                                weather="여름";
                            }else if(resultStr.contains("겨울")){
                                tv_title_type.setText(resultStr.replace("겨울",""));
                                type=resultStr.replace("겨울","");
                                tv_title_weather.setText("겨울");
                                weather="겨울";
                            }
                            //tv_title_type.setText(output.first);
                            //type=resultStr;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

        }
    }

    @Override
    public void onDestroy() {
        cls.finish();
        super.onDestroy();
    }

    private String isLowerGarment(String className) {
        String[] springLowerGarments = {
                "pajama", "slacks", "trousers", "underpants", "jean","abaya","suit"
        };
        String[] summerLowerGarments = {
                "bikini", "jockstrap", "miniskirt", "pantyhose",
                "sarong", "skirt", "stocking", "swimming trunks"
        };
        String[] winterLowerGarments = {
                "tt"
        };
        for (String garment : springLowerGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return "봄하의";
            }
        }
        for (String garment : summerLowerGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return "여름하의";
            }
        }
        for (String garment : winterLowerGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return "겨울하의";
            }
        }
        return "없음";
    }

    private String isOutterGarment(String className){
        String[] springOutterGarments = {
                "cardigan","academic gown","bathrobe","sweatshirt"
        };
        String[] summerOutterGarments = {
                "tt"
        };
        String[] winterOutterGarments = {
                "fur coat","trench coat","bulletproof vest"
        };
        for (String garment : springOutterGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return "봄아우터";
            }
        }
        for (String garment : summerOutterGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return "여름아우터";
            }
        }
        for (String garment : winterOutterGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return "겨울아우터";
            }
        }
        return "없음";
    }

    private String isUpperGarment(String className) {
        String[] springUpperGarments = {
                "velvet","cocktail dress", "dinner jacket", "dressing gown", "gown",
                "kimono", "nightshirt", "pajama",
                "silk dress", "stole", "tunic",
                "tuxedo", "uniform", "veil", "vest"
        };
        String[] summerUpperGarments = {
                "bikini", "binder", "brassiere", "dressing gown",
                "life jacket", "pajama", "sarong", "shawl","jersey"
        };
        String[] winterUpperGarments = {
                "wool", "bonnet", "dressing gown", "hoodie",
                "ski mask"
        };

        for (String garment : springUpperGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return "봄상의";
            }
        }
        for (String garment : summerUpperGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return "여름상의";
            }
        }
        for (String garment : winterUpperGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return "겨울상의";
            }
        }
        return "없음";
    }

    void Save(String type, String weather, String style) {
        String path="";
        String name="";
        Logg.e(Global.USER_HTJ,"저장 타입: "+type);
        Logg.e(Global.USER_HTJ,"저장 날씨: "+weather);
        Logg.e(Global.USER_HTJ,"저장 스타일: "+style);
        Logg.e(Global.USER_HTJ,"data.getInstance(mContext).setNumber(2,type+weather);: "+data.getInstance(mContext).getNumber(type+weather));

        /*if(data.getInstance(mContext).getNumC(type+weather)==9) {
            data.getInstance(mContext).setNumC(0, type + weather);
        }*/
        if(data.getInstance(mContext).getNumber(type+weather)!=18) {
            data.getInstance(mContext).setNumber(data.getInstance(mContext).getNumber(type + weather) + 1, type + weather);
            data.getInstance(mContext).setNumC(data.getInstance(mContext).getNumC(type + weather) + 1, type + weather);
            data.getInstance(mContext).setStyle(style, type + weather, data.getInstance(mContext).getNumber(type + weather));
            name = type + weather + data.getInstance(mContext).getNumC(type + weather) + ".jpg";
            data.getInstance(mContext).setPicture(name, type + weather, data.getInstance(mContext).getNumC(type + weather));
            path = createPictureFilePath(name);
            data.getInstance(mContext).setRegister(type + weather, data.getInstance(mContext).getNumber(type + weather), name);
            Logg.e(Global.USER_HTJ, "저장 name: " + data.getInstance(mContext).getNumC(type + weather));
            Logg.e(Global.USER_HTJ, "저장 name: " + name);
            Logg.e(Global.USER_HTJ, "저장 path: " + path);
            Logg.e(Global.USER_HTJ, "path:" + path);
            saveBitmapAsFile(bitmap, path);
            scanMediaFile(new File(path));
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }else {
            Toast.makeText(mContext, "갯수를 초과했습니다.", Toast.LENGTH_SHORT).show();
        }
    }


    /*void Save(String type, String weather, String style){
        String path="";
        String name="";
        Logg.e(Global.USER_HTJ,"저장 타입: "+type);
        Logg.e(Global.USER_HTJ,"저장 날씨: "+weather);
        Logg.e(Global.USER_HTJ,"저장 스타일: "+style);
        Logg.e(Global.USER_HTJ,"data.getInstance(mContext).setNumber(2,type+weather);: "+data.getInstance(mContext).getNumber(type+weather));


        if(data.getInstance(mContext).getNumber(type+weather)==0){
            data.getInstance(mContext).setNumber(1,type+weather);
            data.getInstance(mContext).setStyle(style,type+weather,1);
            name = type+weather+"1.jpg";
            data.getInstance(mContext).setPicture(name,type+weather,1);
            path = createPictureFilePath(name);
            Logg.e(Global.USER_HTJ,"저장 name: "+1);
            Logg.e(Global.USER_HTJ,"저장 name: "+name);
            Logg.e(Global.USER_HTJ,"저장 path: "+path);

        }else if(data.getInstance(mContext).getNumber(type+weather)==1){
            data.getInstance(mContext).setNumber(2,type+weather);
            data.getInstance(mContext).setStyle(style,type+weather,2);
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
                data.getInstance(mContext).setStyle(style,type+weather,1);
                data.getInstance(mContext).setPicture(name,type+weather,1);
                path = createPictureFilePath(name);
                Logg.e("저장 name: "+name);
                Logg.e("저장 path: "+path);
            }else{
                name = type+weather+"2.jpg";
                data.getInstance(mContext).setStyle(style,type+weather,2);
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
    }*/

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