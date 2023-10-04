package com.han.total;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class data {

    private final static String PREF_NAME = "pref_sharedpreferences_data";
    private static final String NEW_NOTIFY_YN_KEY = "new_notify_yn_key";
    private static final String NEW_NOTE_KEY = "new_note_key";
    private static final String DAY_NOTE_KEY = "day_note_key";
    private static final String NUMBER_KEY = "NUMBER_KEY";
    private static final String NUMBER2_KEY = "NUMBER2_KEY";
    private static final String STYLE_KEY = "STYLE_KEY";
    private static final String CLOTH_KEY = "CLOTH_KEY";
    private static final String CAL_CLOTH_KEY = "CAL_CLOTH_KEY";
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static Context mContext;

    private static data mInstance;
    public synchronized static data getInstance(Context ctx) {
        mContext = ctx;
        if (mInstance == null) {
            mInstance = new data();
            mSharedPreferences = ctx.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();
        }
        return mInstance;
    }

    public void setPicture(String flag, String type,int index){mEditor.putString(NEW_NOTE_KEY+type+index, flag);mEditor.commit();}
    public String getPicture(String type,int index) {return mSharedPreferences.getString(NEW_NOTE_KEY+type+index, "N");}
    public void setNumber(int flag, String type){mEditor.putInt(NUMBER_KEY+type, flag); mEditor.commit();}
    public int getNumber(String type) {
        return mSharedPreferences.getInt(NUMBER_KEY+type, 0);
    }
    public void setNumC(int flag, String type){mEditor.putInt(NUMBER2_KEY+type, flag); mEditor.commit();}
    public int getNumC(String type) {
        return mSharedPreferences.getInt(NUMBER2_KEY+type, 0);
    }
    public void setStyle(String style, String type,int index){mEditor.putString(STYLE_KEY+type+index,style); mEditor.commit();}
    public String getStyle(String type,int index){return mSharedPreferences.getString(STYLE_KEY+type+index,"N");}
    public void setCloth(int flag, String type, int index){mEditor.putInt(CLOTH_KEY+type+index, flag); mEditor.commit();}
    public int getCloth(String type,int index) {return mSharedPreferences.getInt(CLOTH_KEY+type+index, 0);}
    public void setDay(String day,String text){mEditor.putString(DAY_NOTE_KEY+day,text); mEditor.commit();}
    public String getDay(String day) {return mSharedPreferences.getString(DAY_NOTE_KEY+day,"");}
    public void setCalC(String day,String type,String cloth){mEditor.putString(CAL_CLOTH_KEY+day+type, cloth); mEditor.commit();}
    public String getCALC(String day,String type) {return mSharedPreferences.getString(CAL_CLOTH_KEY+day+type,"");}
}
