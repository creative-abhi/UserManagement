package com.usermanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.usermanagement.modelresponse.LoginResponse;

public class SharedPreferenceManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREFS_NAME = "login_prefs";
    private Context context;

    public SharedPreferenceManager(Context context){
        this.context = context;
    }

    public void setUserPreference(LoginResponse.User user){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("id",user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("email", user.getEmail());
        editor.putBoolean("logged", true);
        editor.apply();
    }

    public LoginResponse.User getUserPreference(){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return new LoginResponse.User(sharedPreferences.getString("id",null),sharedPreferences.getString("username",null),
                sharedPreferences.getString("email",null));
    }

    public boolean isLoggedin(){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged",false);
    }
}
