package com.han.total.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.han.total.Activity.Http;
import com.han.total.Data.ListData;
import com.han.total.R;
import com.han.total.data;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListAdapter extends BaseAdapter {

    private TextView list_title, list_id, list_date, list_recommend;
    private ImageView list_top, list_bottom, list_outer;
    private Button list_recommend_btn;
    private ArrayList<ListData> listData = new ArrayList<ListData>();

    private String postData = "";
    private String gd = "";

    private int recomended;

    private ListAdapterListener listAdapterListener;

    public void setListAdapterListener(ListAdapterListener listAdapterListener){
        this.listAdapterListener = listAdapterListener;
    }

    public interface ListAdapterListener{
        void onClick(String postData);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void clear(){
        listData.clear();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listup_item,viewGroup,false);
        }
        list_title = (TextView) view.findViewById(R.id.list_title);
        list_id = (TextView) view.findViewById(R.id.list_id);
        list_date = (TextView) view.findViewById(R.id.list_date);
        list_recommend = (TextView) view.findViewById(R.id.list_recommend);

        list_top = (ImageView) view.findViewById(R.id.list_top);
        list_bottom = (ImageView) view.findViewById(R.id.list_bottom);
        list_outer = (ImageView) view.findViewById(R.id.list_outer);

        list_recommend_btn = (Button) view.findViewById(R.id.list_recommend_btn);

        list_title.setText(listData.get(i).getList_title());
        list_id.setText(listData.get(i).getUser_id());
        list_date.setText(listData.get(i).getList_date());
        list_recommend.setText(listData.get(i).getList_recommend());


        Bitmap topImage = BitmapFactory.decodeFile(listData.get(i).getList_top());
        Bitmap bottomImage = BitmapFactory.decodeFile(listData.get(i).getList_bottom());
        Bitmap outerImage = BitmapFactory.decodeFile(listData.get(i).getList_outer());

        list_top.setImageBitmap(topImage);
        list_outer.setImageBitmap(outerImage);
        list_bottom.setImageBitmap(bottomImage);

        list_recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] pase_data = new String[2];
                pase_data[0] = data.getInstance(context).getLogin_info(); pase_data[1]="1111";
                postData = "mode=reco_confirm&user_id=" + pase_data[0] + "&list_num=" + listData.get(i).getList_num();
                try {
                    gd = new Http().execute(postData).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(gd.contains("not_found")) {
                    postData = "mode=reco_read&list_num=" + listData.get(i).getList_num();
                    try {
                        gd = new Http().execute(postData).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("디버깅","Recommand1 결과 =" + gd);
                    recomended = Integer.parseInt(gd);
                    recomended++;

                    postData = "mode=reco_update&list_num=" + listData.get(i).getList_num() + "&list_reco="+recomended;
                    try {
                        gd = new Http().execute(postData).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("디버깅","Recommend 최종 결과 = " + gd);

                    listData.clear();
                    postData = "mode=listup_get";
                    try {
                        gd = new Http().execute(postData).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("디버깅", "Recommend 최종 결과 list = " + gd);

                    String[] list_parse = gd.split("@");

                    for (int i = 0; i < list_parse.length; i++) {
                        String[] parse_data = list_parse[i].split("#");
                        addItem(parse_data[0],parse_data[1], parse_data[2], context.getCacheDir() + "/"+parse_data[1]+"_"+parse_data[2]+"_top.png", context.getCacheDir() + "/"+parse_data[1]+"_"+parse_data[2]+"_bottom.png", context.getCacheDir() + "/"+parse_data[1]+"_"+parse_data[2]+"_outer.png", parse_data[6], parse_data[7]);
                    }
                    notifyDataSetChanged();

                }else{
                    Toast.makeText(context,"이미 추천한 패션입니다",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    public void addItem(String list_num, String user_id, String list_title, String list_top, String list_bottom, String list_outer, String recommend, String date){
        ListData listData = new ListData();
        listData.setList_num(list_num);
        listData.setList_title(list_title);
        listData.setList_top(list_top);
        listData.setList_bottom(list_bottom);
        listData.setList_outer(list_outer);
        listData.setUser_id(user_id);
        listData.setList_recommend(recommend);
        listData.setList_date(date);
        Log.i("디버깅","리스트 데이터 = " + list_num + "/" + user_id + "/" + list_title + "/" + list_top + "/" + list_bottom + "/" + list_outer + "/" + recommend + "/" + date);
        this.listData.add(listData);
    }
}
