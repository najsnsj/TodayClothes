package com.han.total.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.han.total.Activity.Http;
import com.han.total.R;

import java.util.concurrent.ExecutionException;

public class fragment_tab_info extends Fragment {
    private Context mContext;
    private String login_info;
    private TextView info_name_txt, info_date_txt, info_nic_txt, info_id_txt, info_recommend;
    private String postData = "";
    private String gd = "";

    public fragment_tab_info(Context context, String login_info) {
        this.mContext = context;
        this.login_info = login_info;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_info, container, false);
        //ButterKnife.bind(this, view);
        //info_id_txt = view.findViewById(R.id.info_id_txt) ;
        info_name_txt = view.findViewById(R.id.info_name_txt) ;
        info_date_txt = view.findViewById(R.id.info_date_txt) ;
        info_nic_txt = view.findViewById(R.id.info_nic_txt) ;
        info_recommend = view.findViewById(R.id.info_recommend);

        String[] parse_login_info = login_info.split("@");

       // info_id_txt.setText(parse_login_info[0]);
        info_name_txt.setText(parse_login_info[2]);
        info_nic_txt.setText(parse_login_info[3]);
        info_date_txt.setText(parse_login_info[4]);

        postData = "mode=reco_total&user_id=" + parse_login_info[0];
        try {
            gd = new Http().execute(postData).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(gd.contains("not_found")) {
            info_recommend.setText("0");
        }else{
            Log.i("디버깅","토탈 추천 확인 = " + gd);
            String[] total_data = gd.split("@");
            int total_recommend = 0;
            for(int i=0; i<total_data.length; i++){
                Log.i("디버깅","토탈 추천 하나하나 확인좀 = " + total_data[i]);
                total_recommend = total_recommend + Integer.parseInt(total_data[i]);
            }
            info_recommend.setText(String.valueOf(total_recommend));
        }

        return view;
    }
}
