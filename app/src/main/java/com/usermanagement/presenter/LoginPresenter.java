package com.usermanagement.presenter;

import android.content.Context;
import android.content.Intent;

import com.usermanagement.activity.HomeActivity;
import com.usermanagement.activity.SignupActivity;
import com.usermanagement.api.ApiClient;
import com.usermanagement.model.LoginUser;
import com.usermanagement.modelresponse.LoginResponse;
import com.usermanagement.utils.ProgressDialogManager;
import com.usermanagement.utils.SharedPreferenceManager;
import com.usermanagement.viewcontractor.LoginContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private Context context;
    private ProgressDialogManager progressDialogManager;
    private SharedPreferenceManager preferenceManager;

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        this.context = context;
        progressDialogManager = ProgressDialogManager.getInstance(context);
        preferenceManager = new SharedPreferenceManager(context);
    }

    @Override
    public void onLogin(LoginUser user) {
        progressDialogManager.showProgressDialog();
        Call<LoginResponse> call = ApiClient.getInstance().getApi().getLogin(user.getEmail(),user.getPassword());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialogManager.hideProgressDialog();
                if (response.isSuccessful()){
                    LoginResponse result = response.body();
                    if (result.getError().equals("200")){
                        view.onSuccess(result.getMessage());
                        preferenceManager.setUserPreference(result.getUser());
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }else {
                        view.onError(result.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialogManager.hideProgressDialog();
                view.onError(t.getMessage());
            }
        });
    }

    @Override
    public void onLocateToSignup() {
        Intent intent = new Intent(context, SignupActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


}
