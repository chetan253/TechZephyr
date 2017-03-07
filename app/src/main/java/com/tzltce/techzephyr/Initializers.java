package com.tzltce.techzephyr;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chetan253 on 13/1/17.
 */

public class Initializers {
    public static final String PREF_NAME = "CVSSPref";
    public static final String BASE_EXP = "exploitability";
    public static final String BASE_IMPACT = "impact";
    public static final String BASE_CONF_IMPACT = "confimpact";
    public static final String BASE_INTEG_IMPACT = "integimpact";
    public static final String BASE_AVAIL_IMPACT = "availimpact";
    public static final String BASE_SCORE = "basescore";
    public static final String TEMPORAL_EXP = "temporalexp";
    public static final String TEMPORAL_RC = "temporalrc";
    public static final String TEMPORAL_RL = "temporalrl";
    public static final String TEMPORAL_SCORE = "temporalscore";
    public static final String ENVIRONMENTAL_SCORE = "environmentalscore";
    public static final String MODIFIED_IMPACT = "modifiedimpact";
    public static final String BASE_CREATED = "basecreated";
    public static final String TEMPORAL_CREATED = "temporalcreated";
    public static final String ENVIRONMENTAL_CREATED = "environmentalcreated";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PHOTO_URL = "user_photo_url";
    public static final String USER_HAS_REG = "hasReg";
    public static final String USE_OFFLINE = "useoffline";
    public Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int MODE_PRIVATE = 0;
    public Initializers(Context context){
        this.context =  context;
    }
    public void initialize(){
        initValCreator(BASE_EXP, 0f);
        initValCreator(BASE_IMPACT, 0f);
        initValCreator(BASE_AVAIL_IMPACT, 0f);
        initValCreator(BASE_INTEG_IMPACT, 0f);
        initValCreator(BASE_CONF_IMPACT, 0f);
        initValCreator(BASE_SCORE, 0f);
        initValCreator(TEMPORAL_EXP, 0f);
        initValCreator(TEMPORAL_RL, 0f);
        initValCreator(TEMPORAL_RC, 0f);
        initValCreator(TEMPORAL_SCORE, 0f);
        initValCreator(MODIFIED_IMPACT, 0f);
        initValCreator(ENVIRONMENTAL_SCORE, 0f);
        initValBoolean(BASE_CREATED, false);
        initValBoolean(TEMPORAL_CREATED, false);
        initValBoolean(ENVIRONMENTAL_CREATED, false);
        initValBoolean(USE_OFFLINE, false);
    }
    public void initValCreator(String key, Float value){
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putFloat(key, value);
        editor.commit();
    }
    public void initValBoolean(String key, boolean value){
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
