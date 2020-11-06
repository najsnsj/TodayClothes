package com.han.total.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.han.total.Adapter.TemplateAdapter;
import com.han.total.Fragment.fragment_tab0;
import com.han.total.Fragment.fragment_tab1;
import com.han.total.Fragment.fragment_tab2;
import com.han.total.Fragment.fragment_tab3;
import com.han.total.Interface.OneButtonDialogCallback;
import com.han.total.Interface.TwoButtonDialogCallback;
import com.han.total.R;
import com.han.total.Util.CustomDialog;
import com.han.total.Util.Global;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends AppCompatActivity implements TemplateAdapter.AdapterCallback{

    Context mContext;
    @BindView(R.id.fl_fragment0)
    FrameLayout fl_fragment0;
    @BindView(R.id.fl_fragment1)
    FrameLayout fl_fragment1;
    @BindView(R.id.fl_fragment2)
    FrameLayout fl_fragment2;
    @BindView(R.id.fl_fragment3)
    FrameLayout fl_fragment3;
    @BindView(R.id.ll_bottom_fragment_total)
    LinearLayout ll_bottom_fragment_total;
    @BindView(R.id.fr_fragment)
    FrameLayout fr_fragment;
    @BindView(R.id.template_recycler)
    RecyclerView template_recycler;
    //
    TemplateAdapter.AdapterCallback mAdapterCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //butterknife init
        mContext = this;
        ButterKnife.bind(this);

        InitFragement();
        InitRecylerView(); //RecyclerView
        TestTwoDialog();
    }

    public void InitFragement(){
        if(Global.FRAGMENT_NUMBERS==0){
            ll_bottom_fragment_total.setVisibility(View.GONE);
            fr_fragment.setVisibility(View.GONE);
        }else if(Global.FRAGMENT_NUMBERS==1){
            fl_fragment1.setVisibility(View.GONE);
            fl_fragment2.setVisibility(View.GONE);
            fl_fragment3.setVisibility(View.GONE);
        }else if(Global.FRAGMENT_NUMBERS==2){
            fl_fragment2.setVisibility(View.GONE);
            fl_fragment3.setVisibility(View.GONE);
        }else if(Global.FRAGMENT_NUMBERS==3){
            fl_fragment3.setVisibility(View.GONE);
        }
        if(Global.FRAGMENT_NUMBERS>0) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fr_fragment, new fragment_tab0());
            fragmentTransaction.commit();
        }
    }

    @OnClick({R.id.fl_fragment0,R.id.fl_fragment1,R.id.fl_fragment2,R.id.fl_fragment3}) void BottomTabButton(View v){
        int id = v.getId();
        Fragment fr =  new fragment_tab0();
        if(id==R.id.fl_fragment0){
            fr = new fragment_tab0() ;
        }else if(id==R.id.fl_fragment1){
            fr = new fragment_tab1() ;
        }else if(id==R.id.fl_fragment2){
            fr = new fragment_tab2() ;
        }else if(id==R.id.fl_fragment3){
            fr = new fragment_tab3() ;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fr_fragment, fr);
        fragmentTransaction.commit();
    }

    //RecyclerView
    public void InitRecylerView(){

        template_recycler.setVisibility(View.VISIBLE);
        fr_fragment.setVisibility(View.GONE);

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }
        template_recycler.setLayoutManager(new LinearLayoutManager(this)) ;
        TemplateAdapter adapter = new TemplateAdapter(list,this) ;
        template_recycler.setAdapter(adapter) ;
    }

    //RecyclerView
    @Override
    public void DoSomeThing(int posionion){
        Toast.makeText(mContext,"posion: "+posionion,Toast.LENGTH_SHORT).show();
    }

    public void TestOneDialog(){
        CustomDialog customDialog = new CustomDialog(mContext);
        customDialog.setOneButtonDialogCallback(new OneButtonDialogCallback() {
            @Override
            public void onClickConfirm() {
                Toast.makeText(mContext,"onebutton",Toast.LENGTH_SHORT).show();
            }
        });
        customDialog.show();
    }

    public void TestTwoDialog(){
        CustomDialog customDialog = new CustomDialog(mContext);
        customDialog.setTwoButtonDialogCallback(new TwoButtonDialogCallback() {
            @Override
            public void onClickCancel() {
                Toast.makeText(mContext,"two cancel",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickConfirm() {
                Toast.makeText(mContext,"two confirim",Toast.LENGTH_SHORT).show();
            }
        });
        customDialog.show();
    }


}