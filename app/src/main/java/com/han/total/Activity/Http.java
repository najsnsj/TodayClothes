package com.han.total.Activity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Http extends AsyncTask<String, Void, String> {
    private configs conf;

    @Override
    protected String doInBackground(String... strings) {
        String result = "Error";

        conf = new configs();

        try {
            URL url = new URL(conf.getUrl());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setUseCaches(false);

            conn.setRequestMethod("POST");

            String body = strings[0];

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(body);
            osw.flush();

            InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;

            while((str=reader.readLine())!=null){
                builder.append(str);
            }
            result = builder.toString();

        } catch (MalformedURLException e) {
            Log.i("디버깅","MalformedURLException = "+e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("디버깅","IOException = "+e.toString());
            e.printStackTrace();
        }

        return result;
    }
}