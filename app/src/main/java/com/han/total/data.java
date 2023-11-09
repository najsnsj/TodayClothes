package com.han.total;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;


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
    private static final String REGISTER_CLOTH_KEY = "REGISTER_CLOTH_KEY";
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
    //이미지 저장
    public void setPicture(String flag, String type,int index){mEditor.putString(NEW_NOTE_KEY+type+index, flag);mEditor.commit();}
    public String getPicture(String type,int index) {return mSharedPreferences.getString(NEW_NOTE_KEY+type+index, "N");}
    //타입별 옷 등록 갯수
    public void setNumber(int flag, String type){mEditor.putInt(NUMBER_KEY+type, flag); mEditor.commit();}
    public int getNumber(String type) {
        return mSharedPreferences.getInt(NUMBER_KEY+type, 0);
    }
    //옷 이름 제작 시 번호
    public void setNumC(int flag, String type){mEditor.putInt(NUMBER2_KEY+type, flag); mEditor.commit();}
    public int getNumC(String type) {
        return mSharedPreferences.getInt(NUMBER2_KEY+type, 0);
    }
    //옷 스타일 저장
    public void setStyle(String style, String type,int index){mEditor.putString(STYLE_KEY+type+index,style); mEditor.commit();}
    public String getStyle(String type,int index){return mSharedPreferences.getString(STYLE_KEY+type+index,"N");}
    public void setCloth(int flag, String type, int index){mEditor.putInt(CLOTH_KEY+type+index, flag); mEditor.commit();}
    public int getCloth(String type,int index) {return mSharedPreferences.getInt(CLOTH_KEY+type+index, 0);}
    public void setDay(String cloth,String day){mEditor.putString(DAY_NOTE_KEY+cloth,day); mEditor.commit();}
    public String getDay(String cloth) {return mSharedPreferences.getString(DAY_NOTE_KEY+cloth,"0000 / 00 / 0");}
    public  void DeleteDay(String cloth){mEditor.remove(DAY_NOTE_KEY+cloth); mEditor.apply();}
    //캘린더 날짜별 옷 이름 저장
    public void setCalC(String day,String type,String cloth){mEditor.putString(CAL_CLOTH_KEY+day+type, cloth); mEditor.commit();}
    public String getCALC(String day,String type) {return mSharedPreferences.getString(CAL_CLOTH_KEY+day+type,"");}
    //타입 별 옷 저장 순서 저장
    public void setRegister(String type,int flag,String cloth){mEditor.putString(REGISTER_CLOTH_KEY+type+flag, cloth); mEditor.commit();}
    public String getRegister(String type,int flag) {return mSharedPreferences.getString(REGISTER_CLOTH_KEY+type+flag,"");}
    public void DeleteRegister(String type,int flag) {mEditor.remove(REGISTER_CLOTH_KEY+type+flag); mEditor.apply();}
    public Map<String, String> getAllCALCValues(String type) {
        Map<String, String> calcValues = new HashMap<>();
        Map<String, ?> allEntries = mSharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (key.startsWith(type)) {
                calcValues.put(key, value);
            }
        }
        return calcValues;
    }
}
