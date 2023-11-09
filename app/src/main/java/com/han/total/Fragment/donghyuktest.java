package com.han.total.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.han.total.Activity.MainActivity;
import com.han.total.Activity.SelectClothsActivity;
import com.han.total.R;
import com.han.total.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.OnClick;

public class donghyuktest extends AppCompatActivity {

    private Context mContext;
    public String readDay = null;
    public String date = null;

    public CalendarView calendarView;
    public Button cha_Btn, del_Btn, save_Btn;
    public TextView diaryTextView, textView2, textView3;
    public TextView tv_Recommend;
    Bitmap bitmap;
    public ImageView imageView1;
    public ImageView imageView2;
    public ImageView imageView3;
    public ImageView iv_Recommend;

    private static final int REQUEST_IMAGE_PICK = 1000;
    private static final int REQUEST_IMAGE_PICK_2 = 2000;
    private static final int REQUEST_IMAGE_PICK_3 = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donghyuktest);
        calendarView = findViewById(R.id.calendarView);
        diaryTextView = findViewById(R.id.diaryTextView);
        tv_Recommend = findViewById(R.id.tv_reco);
        iv_Recommend = findViewById(R.id.iv_reco);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        date = getCurrentDate();
        diaryTextView.setText(date);
        recommend(date);
        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", data.getInstance(mContext).getCALC(date, "아우터"), imageView1);
        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", data.getInstance(mContext).getCALC(date, "상의"), imageView2);
        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", data.getInstance(mContext).getCALC(date, "하의"), imageView3);
        /*Intent intent = getIntent();
        String image =  intent.getStringExtra("image");
        int num = intent.getIntExtra("num");*/
        // contextEditText.setText(image);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donghyuktest.this, SelectClothsActivity.class);
                intent.putExtra("weather","봄");
                intent.putExtra("type","아우터");
                intent.putExtra("condition","캘린더");
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donghyuktest.this, SelectClothsActivity.class);
                intent.putExtra("weather","봄");
                intent.putExtra("type","상의");
                intent.putExtra("condition","캘린더");
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donghyuktest.this, SelectClothsActivity.class);
                intent.putExtra("weather","봄");
                intent.putExtra("type","하의");
                intent.putExtra("condition","캘린더");
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                imageView1.setImageResource(0);
                imageView2.setImageResource(0);
                imageView3.setImageResource(0);
                date = String.format("%d / %d / %d", year, month + 1, dayOfMonth);
                diaryTextView.setVisibility(View.VISIBLE);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",data.getInstance(mContext).getCALC(date,"아우터"),imageView1);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",data.getInstance(mContext).getCALC(date,"상의"),imageView2);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",data.getInstance(mContext).getCALC(date,"하의"),imageView3);
                //imageView3.setVisibility(View.INVISIBLE);
                //contextEditText.setVisibility(View.VISIBLE);
                diaryTextView.setText(date);
                //checkDay(year, month, dayOfMonth);
            }
        });

    }

    public String getCurrentDate() {
        // 현재 시간을 가져오는 Calendar 객체 생성
        Calendar calendar = Calendar.getInstance();

        // 현재 날짜를 yyyy-MM-dd 형식의 문자열로 포맷
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / d");
        String currentDate = dateFormat.format(calendar.getTime());

        return currentDate;
    }
    /*public void checkDay(int cYear, int cMonth, int cDay)
    {
        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";
        FileInputStream fis;

        try
        {
            fis = openFileInput(readDay);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str = new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);

            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);
            gallery_Btn.setVisibility(View.INVISIBLE);
            gallery_Btn2.setVisibility(View.INVISIBLE);
            gallery_Btn3.setVisibility(View.INVISIBLE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);

            Bitmap loadedImage = loadImage();
            if (loadedImage != null) {
                imageView.setImageBitmap(loadedImage);
            }



            cha_Btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);
                    gallery_Btn.setVisibility(View.VISIBLE);
                    gallery_Btn2.setVisibility(View.VISIBLE);
                    gallery_Btn3.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    textView2.setText(contextEditText.getText());
                }

            });
            del_Btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    gallery_Btn.setVisibility(View.VISIBLE);
                    gallery_Btn2.setVisibility(View.VISIBLE);
                    gallery_Btn3.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    removeDiary(readDay);
                }
            });
            if (textView2.getText() == null)
            {
                textView2.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                imageView2.setVisibility(View.INVISIBLE);
                imageView3.setVisibility(View.INVISIBLE);
                gallery_Btn.setVisibility(View.VISIBLE);
                gallery_Btn2.setVisibility(View.VISIBLE);
                gallery_Btn3.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView1.setImageBitmap(bitmap);
                    saveImage(bitmap); // 이미지 저장 메소드 호출.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_PICK_2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView2.setImageBitmap(bitmap);
                    saveImage(bitmap); // 이미지 저장 메소드 호출.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_PICK_3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView3.setImageBitmap(bitmap);
                    saveImage(bitmap); // 이미지 저장 메소드 호출.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    private void saveImage(Bitmap bitmap){
        FileOutputStream fos;

        try{
            fos = openFileOutput(readDay + ".png", MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Bitmap loadImage(){
        FileInputStream fis;

        try{
            fis=openFileInput(readDay + ".png");
            Bitmap b= BitmapFactory.decodeStream(fis);
            fis.close();

            return b;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private void loadImageFromStorage(String path, String name,ImageView iv) {
        try {
            File f;
            f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            if(iv.equals(imageView1)){
                imageView1.setImageBitmap(b);
            }else if(iv.equals(imageView2)) {
                imageView2.setImageBitmap(b);
            }else if(iv.equals(imageView3)){
                imageView3.setImageBitmap(b);
            }else{
                iv_Recommend.setImageBitmap(b);
            }
        } catch (FileNotFoundException e) {
            Log.e("HAN", "exception: " + e);
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = "";
            fos.write((content).getBytes());
            fos.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void recommend(String date) {
        int num = Random(2);
        if(num==0){
            int os1 = 0;int os2 = 0;int os3 = 0;int us1 = 0;int us2 = 0;int us3 = 0;
            int ps1 = 0;int ps2 = 0;int ps3 = 0;int s1 = 0;int s2 = 0;int s3 = 0;
            int o=0; int u=0; int p=0;
            String a=null;
            for (Map.Entry<String, String> entry : data.getInstance(mContext).getAllCALCValues("STYLE_KEY").entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                key = key.replace("STYLE_KEY", "");
                if(key.contains("아우터")){
                    if(value.equals("스포츠")){
                        os1++;
                    }else if(value.equals("캐주얼")){
                        os2++;
                    }else{
                        os3++;
                    }
                    o++;
                }else if(key.contains("상의")){
                    if(value.equals("스포츠")){
                        us1++;
                    }else if(value.equals("캐주얼")){
                        us2++;
                    }else{
                        us3++;
                    }
                    u++;
                }else if(key.contains("하의")){
                    if(value.equals("스포츠")){
                        ps1++;
                    }else if(value.equals("캐주얼")){
                        ps2++;
                    }else{
                        ps3++;
                    }
                    p++;
                }

            }
            int s = Random(3);
            while(true) {
                if (s == 0 && o != 0) {
                    String style = null;
                    if (os1 <= os2) {
                        if (os2 <= os3) {
                            style = "클래식";
                        } else {
                            style = "캐주얼";
                        }
                    } else if (os1 <= os3) {
                        style = "클래식";
                    } else {
                        style = "스포츠";
                    }
                    tv_Recommend.setText("아우터 중 가장 많은 스타일은 " + style + " 입니다.");
                    break;
                } else if (s == 1 && u != 0) {
                    String style = null;
                    if (us1 <= us2) {
                        if (us2 <= us3) {
                            style = "클래식";
                        } else {
                            style = "캐주얼";
                        }
                    } else if (us1 <= us3) {
                        style = "클래식";
                    } else {
                        style = "스포츠";
                    }
                    tv_Recommend.setText("상의 중 가장 많은 스타일은 " + style + " 입니다.");
                    break;
                } else if (s == 2 && p != 0) {
                    String style = null;
                    if (ps1 <= ps2) {
                        if (ps2 <= ps3) {
                            style = "클래식";
                        } else {
                            style = "캐주얼";
                        }
                    } else if (ps1 <= ps3) {
                        style = "클래식";
                    } else {
                        style = "스포츠";
                    }
                    tv_Recommend.setText("하의 중 가장 많은 스타일은 " + style + " 입니다.");
                    break;
                } else if(s == 3 && o!=0 && u!=0 && p!=0){
                    s1 = os1 + us1 + ps1;
                    s2 = os2 + us2 + ps2;
                    s3 = os3 + us3 + ps3;
                    String style = null;
                    if (s1 <= s2) {
                        if (s2 <= s3) {
                            style = "클래식";
                        } else {
                            style = "캐주얼";
                        }
                    } else if (s1 <= s3) {
                        style = "클래식";
                    } else {
                        style = "스포츠";
                    }
                    tv_Recommend.setText("가장 많이 가지고 있는 스타일은 " + style + " 입니다.");
                    break;
                }else if(o==0 && u==0 && p==0){
                    tv_Recommend.setText("캘린더에 옷을 등록해보세요.");
                    break;
                }else{
                    s = Random(3);
                }
            }
        } else if(num==1) {
            Map<String, String> allCALCValues = data.getInstance(mContext).getAllCALCValues("day_note_key");
            String[] clothes = new String[allCALCValues.size()];
            String[] day = new String[allCALCValues.size()];
            int n = 0;
            for (Map.Entry<String, String> entry : data.getInstance(mContext).getAllCALCValues("day_note_key").entrySet()) {
                String key = entry.getKey();
                day[n] = entry.getValue();
                clothes[n] = key.replace("day_note_key", "");
                n++;
            }
            if (n == 0) {
                tv_Recommend.setText("캘린더에 옷을 등록해보세요.");
            } else {
                int numR = Random(n - 1);
                tv_Recommend.setText(" 마지막으로 이 의상을 입은 날은 \n " + day[numR] + "일 입니다.");
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", clothes[numR], iv_Recommend);
            }
        }else if(num==2){
            //3개월 동안 가장 많이 입은 옷
            Map<String, Integer> calCloths = new HashMap<>();
            String[] today = date.split(" / ");
            int n = -1;
            int TNum= -1; int num1 = -1;int num2 = -1;int num3 = -1;
            String Tcloth=null;String cloth1=null;String cloth2=null;String cloth3=null;
            int m1 = Integer.parseInt(today[1])-1; String month1 = String.valueOf(m1);
            int m2 = Integer.parseInt(today[1])-2; String month2 = String.valueOf(m2);
            for (Map.Entry<String, String> entry : data.getInstance(mContext).getAllCALCValues("CAL_CLOTH_KEY").entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                key = key.replace("CAL_CLOTH_KEY", "");
                if(key.contains(today[0])&&key.contains(today[1])){
                    if(calCloths.containsKey(value)){
                        calCloths.put(value, calCloths.get(value)+1);
                    }else{
                        calCloths.put(value,1);
                    }
                }else if(key.contains(today[0])&&key.contains(month1)){
                    if(calCloths.containsKey(value)){
                        calCloths.put(value, calCloths.get(value)+1);
                    }else{
                        calCloths.put(value,1);
                    }
                }else if(key.contains(today[0])&&key.contains(month2)){
                    if(calCloths.containsKey(value)){
                        calCloths.put(value, calCloths.get(value)+1);
                    }else{
                        calCloths.put(value,1);
                    }
                }
            }
            for(String key: calCloths.keySet()){
                if(TNum<calCloths.get(key)){
                    Tcloth = key;
                    TNum=calCloths.get(key);
                }
                if(key.contains("아우터")&&num1<calCloths.get(key)){
                    cloth1 = key;
                    num1 = calCloths.get(key);
                }
                if(key.contains("상의")&&num2<calCloths.get(key)){
                    cloth2 = key;
                    num2 = calCloths.get(key);
                }
                if(key.contains("하의")&&num3<calCloths.get(key)){
                    cloth3 = key;
                    num3 = calCloths.get(key);
                }
            }
            n = Random(3);
            while(true) {
                if (n == 0 && TNum != -1) {
                    tv_Recommend.setText(" 최근 3개월간 가장 많이 입은 옷은\n " + Tcloth + "입니다.");
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", Tcloth, iv_Recommend);
                    break;
                } else if (n == 1 && num1 != -1) {
                    tv_Recommend.setText(" 최근 3개월간 가장 많이 입은 아우터는\n " + cloth1 + "입니다.");
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", cloth1, iv_Recommend);
                    break;
                } else if (n == 2 && num2 != -1) {
                    tv_Recommend.setText(" 최근 3개월간 가장 많이 입은 상의는\n " + cloth2 + "입니다.");
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", cloth2, iv_Recommend);
                    break;
                } else if (n == 3 && num3 != -1) {
                    tv_Recommend.setText(" 최근 3개월간 가장 많이 입은 하의는\n " + cloth3 + "입니다.");
                    loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", cloth3, iv_Recommend);
                    break;
                } else if (TNum == -1 && num1 == -1 && num2 == -1 && num3 == -1) {
                    tv_Recommend.setText("캘린더에 옷을 등록해보세요.");
                    break;
                } else {
                    n = Random(3);
                }
            }
        }
    }

    int Random(int i) {
        if (i < 0) {
            return 0; // i가 음수일 때 0을 반환
        } else {
            int max_num_value = i;
            int min_num_value = 0;

            Random random = new Random();

            int randomNum = random.nextInt(max_num_value - min_num_value + 1) + min_num_value;
            return randomNum;
        }
    }


   /* @SuppressLint("WrongConstant")
    public void saveDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }*/
}