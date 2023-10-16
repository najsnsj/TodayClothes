package com.han.total.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.han.total.R;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;
import com.han.total.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.ViewHolder> {
@BindView(R.id.tv_sport2)
TextView tv_sports;
    @BindView(R.id.tv_title_style2)
    TextView tv_title;
    //
    public interface AdapterCallback {
        void DoSomeThing(int position);
    }

    private ArrayList<String> mData = null ;
    private AdapterCallback mAdapterCallback;
    Context mContext;
    boolean onoff=true;
    private String style = null;

    public TemplateAdapter(ArrayList<String> list,TemplateAdapter.AdapterCallback AdapterCallback,Context context,String Cstyle) {
        mData = list ;
        mContext = context;
        onoff = true;
        mAdapterCallback = AdapterCallback;
        style = Cstyle;
    }

    public TemplateAdapter(ArrayList<String> list,TemplateAdapter.AdapterCallback AdapterCallback,Context context,boolean flag) {
        mData = list ;
        mContext = context;
        onoff = flag;
        mAdapterCallback = AdapterCallback;

    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_img0 ;
        ImageView iv_img1;
        ImageView iv_img2;
        ViewHolder(View itemView) {
            super(itemView) ;
            iv_img0 = itemView.findViewById(R.id.iv_img0) ;
            iv_img1 = itemView.findViewById(R.id.iv_img1);
            iv_img2 = itemView.findViewById(R.id.iv_img2);

            iv_img0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAdapterCallback != null) {
                        Logg.e(Global.USER_HTJ,"mSelectedIndex: "+getLayoutPosition());
                        mAdapterCallback.DoSomeThing(getLayoutPosition());
                    }
                }
            });

            iv_img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAdapterCallback != null) {
                        Logg.e(Global.USER_HTJ,"mSelectedIndex: "+getLayoutPosition());
                        mAdapterCallback.DoSomeThing(getLayoutPosition());
                    }
                }
            });

            iv_img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAdapterCallback != null) {
                        Logg.e(Global.USER_HTJ,"mSelectedIndex: "+getLayoutPosition());
                        mAdapterCallback.DoSomeThing(getLayoutPosition());
                    }
                }
            });
        }

    }

    @Override
    public TemplateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.template_adapter_item, parent, false) ;
        TemplateAdapter.ViewHolder vh = new TemplateAdapter.ViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(TemplateAdapter.ViewHolder holder, int position) {
        String text = mData.get(position);
        int temp = Integer.parseInt(text);
        String season;
        if (temp > 20) {season = "여름";} else if (temp > 5) {season = "봄";} else {season = "겨울";}
        classifyItemByStyle(holder, season);
    }


    private void classifyItemByStyle(TemplateAdapter.ViewHolder holder, String season) {
        String name1 = "";
        String name2 = "";
        String name3 = "";
        ArrayList<String> CList1 = new ArrayList<>();
        ArrayList<String> CList2 = new ArrayList<>();
        ArrayList<String> CList3 = new ArrayList<>();
        if (onoff) {
            if (style.equals("스포츠")) {
                for (int i = 1; i < 10; i++) {
                    if (data.getInstance(mContext).getStyle("상의" + season, i).equals("스포츠")) {
                        CList1.add(data.getInstance(mContext).getPicture("상의" + season, i));
                    }
                    if (data.getInstance(mContext).getStyle("아우터" + season, i).equals("스포츠")) {
                        CList2.add(data.getInstance(mContext).getPicture("아우터" + season, i));
                    }
                    if (data.getInstance(mContext).getStyle("하의" + season, i).equals("스포츠")) {
                        CList3.add(data.getInstance(mContext).getPicture("하의" + season, i));
                    }
                }
            } else if (style.equals("캐주얼")) {
                for (int i = 1; i < 10; i++) {
                    if (data.getInstance(mContext).getStyle("상의" + season, i).equals("캐주얼")) {
                        CList1.add(data.getInstance(mContext).getPicture("상의" + season, i));
                    }
                    if (data.getInstance(mContext).getStyle("아우터" + season, i).equals("캐주얼")) {
                        CList2.add(data.getInstance(mContext).getPicture("아우터" + season, i));
                    }
                    if (data.getInstance(mContext).getStyle("하의" + season, i).equals("캐주얼")) {
                        CList3.add(data.getInstance(mContext).getPicture("하의" + season, i));
                    }
                }
            } else if (style.equals("클래식")) {
                for (int i = 1; i < 10; i++) {
                    if (data.getInstance(mContext).getStyle("상의" + season, i).equals("클래식")) {
                        CList1.add(data.getInstance(mContext).getPicture("상의" + season, i));
                    }
                    if (data.getInstance(mContext).getStyle("아우터" + season, i).equals("클래식")) {
                        CList2.add(data.getInstance(mContext).getPicture("아우터" + season, i));
                    }
                    if (data.getInstance(mContext).getStyle("하의" + season, i).equals("클래식")) {
                        CList3.add(data.getInstance(mContext).getPicture("하의" + season, i));
                    }
                }
            }
            if (CList1.size() < 1) {
                holder.iv_img0.setImageResource(0);
            } else {
                name1 = CList1.get(Random(CList1.size()) - 1);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name1, holder, 0);
            }
            if (CList2.size() < 1) {
                holder.iv_img1.setImageResource(0);
            } else {
                name2 = CList2.get(Random(CList2.size()) - 1);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name2, holder, 1);
            }
            if (CList3.size() < 1) {
                holder.iv_img2.setImageResource(0);
            } else {
                name3 = CList3.get(Random(CList3.size()) - 1);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name3, holder, 2);
            }
            if (style.equals("스타일")) {
                name1 = data.getInstance(mContext).getPicture("상의" + season, Random(data.getInstance(mContext).getNumber("상의" + season)));
                name2 = data.getInstance(mContext).getPicture("아우터" + season, Random(data.getInstance(mContext).getNumber("아우터" + season)));
                name3 = data.getInstance(mContext).getPicture("하의" + season, Random(data.getInstance(mContext).getNumber("하의" + season)));
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name1, holder, 0);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name2, holder, 1);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/", name3, holder, 2);
            }
        } else {
            holder.iv_img0.setImageResource(0);
            holder.iv_img1.setImageResource(0);
            holder.iv_img2.setImageResource(0);
        }
    }

    /*@Override
    public void onBindViewHolder(TemplateAdapter.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        int temp = Integer.parseInt(text);
        if(onoff) {
            Logg.e(Global.USER_HTJ,"온도: "+temp);
            if (temp > 20) {  //여름
                String name1 = data.getInstance(mContext).getPicture("상의여름",Random(data.getInstance(mContext).getNumber("상의여름")));
                String name2 = data.getInstance(mContext).getPicture("아우터여름",Random(data.getInstance(mContext).getNumber("아우터여름")));
                String name3 = data.getInstance(mContext).getPicture("하의여름",Random(data.getInstance(mContext).getNumber("하의여름")));
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name1,holder,0);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name2,holder,1);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name3,holder,2);
            } else if (temp > 5) { // 봄
                String name1 = data.getInstance(mContext).getPicture("상의봄",Random(data.getInstance(mContext).getNumber("상의봄")));
                String name2 = data.getInstance(mContext).getPicture("아우터봄",Random(data.getInstance(mContext).getNumber("아우터봄")));
                String name3 = data.getInstance(mContext).getPicture("하의봄",Random(data.getInstance(mContext).getNumber("하의봄")));
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name1,holder,0);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name2,holder,1);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name3,holder,2);
            } else { // 겨울
                String name1 = data.getInstance(mContext).getPicture("상의겨울",Random(data.getInstance(mContext).getNumber("상의겨울")));
                String name2 = data.getInstance(mContext).getPicture("아우터겨울",Random(data.getInstance(mContext).getNumber("아우터겨울")));
                String name3 = data.getInstance(mContext).getPicture("하의겨울",Random(data.getInstance(mContext).getNumber("하의겨울")));
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name1,holder,0);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name2,holder,1);
                loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name3,holder,2);
            }
        }else{
            holder.iv_img0.setImageResource(data.getInstance(mContext).getCloth( "0", position));
            holder.iv_img1.setImageResource(data.getInstance(mContext).getCloth( "1", position));
            holder.iv_img2.setImageResource(data.getInstance(mContext).getCloth( "2", position));
        }
    }*/

    private void loadImageFromStorage(String path, String name,ViewHolder holder,int i)
    {
        try {
            File f;
            f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            switch (i){
                case 0:
                    holder.iv_img0.setImageBitmap(b); break;
                case 1:
                    holder.iv_img1.setImageBitmap(b); break;
                case 2:
                    holder.iv_img2.setImageBitmap(b); break;
            }


        }
        catch (FileNotFoundException e)
        {
            Log.e("HAN","exception: "+e);
            e.printStackTrace();
        }
    }

   /* @Override
    public void onBindViewHolder(TemplateAdapter.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        int temp = Integer.parseInt(text);

        if(onoff) {
            Logg.e(Global.USER_HTJ,"온도: "+temp);
            if (temp > 20) {  //여름
                int drawableResId1 = mContext.getResources().getIdentifier("summertop" + Random(), "drawable", mContext.getPackageName());
                //int drawableResId2 = mContext.getResources().getIdentifier("summerouter" + Random(), "drawable", mContext.getPackageName());
                String imagePath = "/storage/emulated/0/DCIM/Camera/아우터여름1.jpg";
                int drawableResId3 = mContext.getResources().getIdentifier("summerbottom" + Random(), "drawable", mContext.getPackageName());
                //lic void setCloth(int flag, String type, int index){
                data.getInstance(mContext).setCloth(drawableResId1, "0", position);
                //data.getInstance(mContext).setCloth(drawableResId2, "1", position);
                data.getInstance(mContext).setCloth(drawableResId3, "2", position);
                holder.iv_img0.setImageResource(drawableResId1);
                Glide.with(mContext).load(imagePath).into(holder.iv_img1);
                //holder.iv_img1.setImageResource(drawableResId2);
                holder.iv_img2.setImageResource(drawableResId3);
            } else if (temp > 5) { // 봄
                int drawableResId1 = mContext.getResources().getIdentifier("springtop" + Random(), "drawable", mContext.getPackageName());
                int drawableResId2 = mContext.getResources().getIdentifier("springouter" + Random(), "drawable", mContext.getPackageName());
                int drawableResId3 = mContext.getResources().getIdentifier("springbottom" + Random(), "drawable", mContext.getPackageName());
                data.getInstance(mContext).setCloth(drawableResId1, "0", position);
                data.getInstance(mContext).setCloth(drawableResId2, "1", position);
                data.getInstance(mContext).setCloth(drawableResId3, "2", position);
                holder.iv_img0.setImageResource(drawableResId1);
                holder.iv_img1.setImageResource(drawableResId2);
                holder.iv_img2.setImageResource(drawableResId3);
            } else { // 겨울
                int drawableResId1 = mContext.getResources().getIdentifier("wintertop" + Random(), "drawable", mContext.getPackageName());
                int drawableResId2 = mContext.getResources().getIdentifier("winterouter" + Random(), "drawable", mContext.getPackageName());
                int drawableResId3 = mContext.getResources().getIdentifier("winterbottom" + Random(), "drawable", mContext.getPackageName());
                data.getInstance(mContext).setCloth(drawableResId1, "0", position);
                data.getInstance(mContext).setCloth(drawableResId2, "1", position);
                data.getInstance(mContext).setCloth(drawableResId3, "2", position);
                holder.iv_img0.setImageResource(drawableResId1);
                holder.iv_img1.setImageResource(drawableResId2);
                holder.iv_img2.setImageResource(drawableResId3);
            }
        }else{
            holder.iv_img0.setImageResource(data.getInstance(mContext).getCloth( "0", position));
            holder.iv_img1.setImageResource(data.getInstance(mContext).getCloth( "1", position));
            holder.iv_img2.setImageResource(data.getInstance(mContext).getCloth( "2", position));
        }
        //holder.textView1.setText(text) ;
    }*/


    int Random(int i){
        if (i == 0) {
            return 0;
        }else {
            int max_num_value = i;
            int min_num_value = 1;

            Random random = new Random();

            int randomNum = random.nextInt(max_num_value - min_num_value + 1) + min_num_value;
            return randomNum;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}