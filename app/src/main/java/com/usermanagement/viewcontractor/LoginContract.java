package com.usermanagement.viewcontractor;

import android.content.Context;

import com.usermanagement.model.LoginUser;

public interface LoginContract {

    public interface View{
        void onSuccess(String response);
        void onError(String response);
    }

    public interface Presenter{
        void onLogin(LoginUser user);
        void onLocateToSignup();
    }

}
