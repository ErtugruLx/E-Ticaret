package com.onurcicek.eticaret.helper;

/**
 * Created by plox on 03.01.2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bilgisaya on 27.11.2016.
 */
public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "BirBileneSor";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private String KEY_IS_ACTIVITY= "activity";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }


    public boolean isLoggedIn(){
        System.out.println(pref);
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setActivity(boolean isActivity){
        editor.putBoolean(KEY_IS_ACTIVITY,isActivity);
        editor.commit();
    }

    public boolean isActivity(){
        System.out.println(pref);
        return pref.getBoolean(KEY_IS_ACTIVITY, false);
    }


}