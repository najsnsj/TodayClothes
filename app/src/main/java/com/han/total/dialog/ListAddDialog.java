package com.han.total.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.han.total.Activity.Http;
import com.han.total.Fragment.donghyuktest;
import com.han.total.R;
import com.han.total.ScpUtil;
import com.han.total.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutionException;

public class ListAddDialog extends Dialog {
    private Context context;
    private ImageView up_outer, up_top, up_bottom;
    private EditText up_edit;
    private Button listup_btn;

    //private int position;
    private String login_info;
    private String date;

    private String postData = "";
    private String gd = "";

    public ListAddDialog(Context context, String date, String login_info) {
        super(context);
        this.context = context;
        this.date = date;
        //this.position = position;
        this.login_info = login_info;
    }

    private static class ScpTransferTask extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String host = params[2];
            int port = Integer.parseInt(params[3]);
            String localFilePath = params[4];
            String remoteFilePath = params[5];

            ScpUtil.sendFile(username, password, host, port, localFilePath, remoteFilePath);
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_listup);
        up_bottom = (ImageView) findViewById(R.id.up_bottom);
        up_outer = (ImageView) findViewById(R.id.up_outer);
        up_top = (ImageView) findViewById(R.id.up_top);
        up_edit = (EditText) findViewById(R.id.up_edit);
        listup_btn = (Button) findViewById(R.id.listup_btn);

        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",data.getInstance(context).getCALC(date,"아우터"),up_outer);
        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",data.getInstance(context).getCALC(date,"상의"),up_top);
        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",data.getInstance(context).getCALC(date,"하의"),up_bottom);


        Log.i("디버깅","로그인정보 확인 = " + login_info);
        String[] login_parse = login_info.split("@");

        listup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        // 이미지 데이터 가져오기
                if(up_edit.getText().toString().length()!= 0) {

                    String topImagePath = saveImageToFile(up_top, "top.png");
                    String outerImagePath = saveImageToFile(up_outer, "outer.png");
                    String bottomImagePath = saveImageToFile(up_bottom, "bottom.png");

                    new ScpTransferTask().execute("root","!nice1586","localhost","22",topImagePath,"/var/www/html/choi/"+login_parse[0]+"/"+up_edit.getText().toString()+"_top.png");
                    new ScpTransferTask().execute("root","!nice1586","localhost","22",outerImagePath,"/var/www/html/choi/"+login_parse[0]+"/"+up_edit.getText().toString()+"_outer.png");
                    new ScpTransferTask().execute("root","!nice1586","localhost","22",bottomImagePath,"/var/www/html/choi/"+login_parse[0]+"/"+up_edit.getText().toString()+"_bottom.png");

                    postData = "mode=listup&user_id=" + login_parse[0] + "&list_title=" + up_edit.getText().toString() + "&list_top=" + "/var/www/html/choi/"+login_parse[0]+"/"+up_edit.getText().toString()+"_top.png" +
                            "&list_bottom=" + "/var/www/html/choi/"+login_parse[0]+"/"+up_edit.getText().toString()+"_bottom.png" + "&list_outer=" + "/var/www/html/choi/"+login_parse[0]+"/"+up_edit.getText().toString()+"_outer.png";
                    try {
                        gd = new Http().execute(postData).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.i("디버깅", "패션저장 결과 = " + gd);

                    dismiss();
                }else{
                    Toast.makeText(context,"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadImageFromStorage(String path, String name,ImageView iv) {
        try {
            File f;
            f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            if(iv.equals(up_top)){
                up_top.setImageBitmap(b);
            }else if(iv.equals(up_outer)) {
                up_outer.setImageBitmap(b);
            }else if(iv.equals(up_bottom)){
                up_bottom.setImageBitmap(b);
            }
        } catch (FileNotFoundException e) {
            Log.e("HAN", "exception: " + e);
            e.printStackTrace();
        }
    }

    private String saveImageToFile(ImageView imageView, String fileName) {
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();

        FileOutputStream outStream = null;
        File file = new File(context.getCacheDir(), fileName);
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
