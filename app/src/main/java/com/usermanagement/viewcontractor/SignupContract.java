package com.usermanagement.viewcontractor;

import com.usermanagement.model.SignupUser;

public interface SignupContract {
    interface View {
        void onSuccess(String result);
        void onError(String result);
    }

    interface Presenter{
        void onRegister(SignupUser user);
        void onlocateToSignin();
    }
}
