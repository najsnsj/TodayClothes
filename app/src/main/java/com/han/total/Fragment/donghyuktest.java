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

import butterknife.OnClick;

public class donghyuktest extends AppCompatActivity {

    private Context mContext;
    public String readDay = null;
    public String date = null;

    public CalendarView calendarView;
    public Button cha_Btn, del_Btn, save_Btn;
    public TextView diaryTextView, textView2, textView3;
    public EditText contextEditText;
    Bitmap bitmap;
    public ImageView imageView1;
    public ImageView imageView2;
    public ImageView imageView3;

    private static final int REQUEST_IMAGE_PICK = 1000;
    private static final int REQUEST_IMAGE_PICK_2 = 2000;
    private static final int REQUEST_IMAGE_PICK_3 = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donghyuktest);
        calendarView = findViewById(R.id.calendarView);
        diaryTextView = findViewById(R.id.diaryTextView);
        contextEditText = findViewById(R.id.contextEditText);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        save_Btn = findViewById(R.id.save_Btn);
        cha_Btn = findViewById(R.id.cha_Btn);
        date = getCurrentDate();
        diaryTextView.setText(date);
        contextEditText.setFocusable(false);
        contextEditText.setText(data.getInstance(mContext).getDay(date));
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
                finish();
                /*intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);*/
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
                finish();
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
                finish();
            }
        });
        imageView1.setClickable(false);
        imageView2.setClickable(false);
        imageView3.setClickable(false);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                imageView1.setImageResource(0);
                imageView1.setClickable(false);
                imageView2.setImageResource(0);
                imageView2.setClickable(false);
                imageView3.setImageResource(0);
                imageView3.setClickable(false);
                contextEditText.setFocusable(false);
                date = String.format("%d / %d / %d", year, month + 1, dayOfMonth);
                diaryTextView.setVisibility(View.VISIBLE);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",data.getInstance(mContext).getCALC(date,"아우터"),imageView1);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",data.getInstance(mContext).getCALC(date,"상의"),imageView2);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",data.getInstance(mContext).getCALC(date,"하의"),imageView3);
                //imageView3.setVisibility(View.INVISIBLE);
                //contextEditText.setVisibility(View.VISIBLE);
                diaryTextView.setText(date);
                contextEditText.setText(data.getInstance(mContext).getDay(date));
                //checkDay(year, month, dayOfMonth);
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                data.getInstance(mContext).setDay(date,contextEditText.getText().toString());
                contextEditText.setFocusable(false);
                imageView1.setClickable(false);
                imageView2.setClickable(false);
                imageView3.setClickable(false);
            }
        });

        cha_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                contextEditText.setFocusable(true);
                contextEditText.setFocusableInTouchMode(true);
                contextEditText.setClickable(true);
                imageView1.setClickable(true);
                imageView2.setClickable(true);
                imageView3.setClickable(true);
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
            }else imageView3.setImageBitmap(b);
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

    @SuppressLint("WrongConstant")
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

    }
}