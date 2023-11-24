package com.han.total.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.han.total.Activity.Http;
import com.han.total.Adapter.ListAdapter;
import com.han.total.R;
import com.han.total.ScpUtil;
import com.han.total.data;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;

public class fragment_tab_list extends Fragment {

    private Context mContext;
    private ListView list_view;
    private EditText search_edit;
    private LinearLayout search_btn;

    private ListAdapter listAdapter;
    private String postData = "";
    private String gd = "";

    public fragment_tab_list(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_list, container, false);
        ButterKnife.bind(this, view);

        listAdapter = new ListAdapter();
        list_view = (ListView) view.findViewById(R.id.list_view);
        list_view.setAdapter(listAdapter);

        listAdapter.setListAdapterListener(new ListAdapter.ListAdapterListener() {
            @Override
            public void onClick(String postData) {
                listAdapter.clear();
                String[] list_parse = gd.split("@");
                for (int i = 0; i < list_parse.length; i++) {
                    String[] parse_data = list_parse[i].split("#");
                    listAdapter.addItem(parse_data[0],parse_data[1], parse_data[2], mContext.getCacheDir() + "/"+parse_data[1]+"_"+parse_data[2]+"_top.png", mContext.getCacheDir() + "/"+parse_data[1]+"_"+parse_data[2]+"_bottom.png", mContext.getCacheDir() + "/"+parse_data[1]+"_"+parse_data[2]+"_outer.png", parse_data[6], parse_data[7]);
                }
                listAdapter.notifyDataSetChanged();
            }
        });


        list_view = (ListView) view.findViewById(R.id.list_view);
        list_view.setAdapter(listAdapter);

        search_edit = (EditText) view.findViewById(R.id.search_edit);
        search_btn = (LinearLayout) view.findViewById(R.id.search_btn);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_edit.getText().toString().length() != 0){
                    postData = "mode=search_list&search=" + search_edit.getText().toString();
                    try {
                        gd = new Http().execute(postData).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    listAdapter.clear();
                    String[] list_parse = gd.split("@");
                    for (int i = 0; i < list_parse.length; i++) {
                        String[] parse_data = list_parse[i].split("#");
                        listAdapter.addItem(parse_data[0],parse_data[1], parse_data[2], mContext.getCacheDir() + "/"+parse_data[1]+"_"+parse_data[2]+"_top.png", mContext.getCacheDir() + "/"+parse_data[1]+"_"+parse_data[2]+"_bottom.png", mContext.getCacheDir() + "/"+parse_data[1]+"_"+parse_data[2]+"_outer.png", parse_data[6], parse_data[7]);
                    }
                    listAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(mContext,"검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        postData = "mode=listup_get";
        try {
            gd = new Http().execute(postData).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("디버깅","리스트 결과 = " + gd);
        listAdapter.clear();
        String[] list_parse = gd.split("@");
        for (int i = 0; i < list_parse.length; i++) {
            String[] parse_data = list_parse[i].split("#");

            String topImagePath = mContext.getCacheDir() + "/" + parse_data[1] + "_" + parse_data[2] + "_top.png";
            String bottomImagePath = mContext.getCacheDir() + "/" + parse_data[1] + "_" + parse_data[2] + "_bottom.png";
            String outerImagePath = mContext.getCacheDir() + "/" + parse_data[1] + "_" + parse_data[2] + "_outer.png";

            if (!new File(topImagePath).exists()) {
                new ScpDownloadTask().execute("root", "!nice1586", "14.63.223.16", "22", parse_data[3], topImagePath);
            }
            if (!new File(bottomImagePath).exists()) {
                new ScpDownloadTask().execute("root", "!nice1586", "14.63.223.16", "22", parse_data[4], bottomImagePath);
            }
            if (!new File(outerImagePath).exists()) {
                new ScpDownloadTask().execute("root", "!nice1586", "14.63.223.16", "22", parse_data[5], outerImagePath);
            }

            listAdapter.addItem(parse_data[0], parse_data[1], parse_data[2], topImagePath, bottomImagePath, outerImagePath, parse_data[6], parse_data[7]);
        }
        listAdapter.notifyDataSetChanged();

        return view;
    }

    private static class ScpDownloadTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String host = params[2];
            int port = Integer.parseInt(params[3]);
            String remoteFilePath = params[4];
            String localFilePath = params[5];

            // SCP를 사용하여 파일 다운로드
            ScpUtil.receiveFile(username, password, host, port, remoteFilePath, localFilePath);

            return localFilePath;
        }
    }

    private void setImageToImageView(String filePath, ImageView imageView) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        imageView.setImageBitmap(bitmap);
    }
}
