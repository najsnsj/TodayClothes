package com.han.total.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.han.total.R;
import com.han.total.data;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    private Button login_btn;
    private EditText edit_id;
    private  EditText edit_name;
    private String postData = "";
    private String gd = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        login_btn = findViewById(R.id.login_btn);
        edit_id = findViewById(R.id.edit_id);
        edit_name = findViewById(R.id.edit_name);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((edit_id.getText().length() != 0)) {
                    postData = "mode=user_regist&user_id=" + edit_id.getText().toString() + "&user_pw=" + "1111" + "&name=" +
                            edit_name.getText().toString() + "&nic_name=" + edit_id.getText().toString();
                    data.getInstance(mContext).setLogin_info(edit_id.getText().toString());
                    try {
                        gd = new Http().execute(postData).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("디버깅", "로그인 요청 결과값 = " + gd);
                    if (gd.contains("already")) {
                        Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임입니다", Toast.LENGTH_SHORT).show();
                    } else if (gd.contains("ok")) {
                        Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user", gd);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "오류가 발생했습니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
