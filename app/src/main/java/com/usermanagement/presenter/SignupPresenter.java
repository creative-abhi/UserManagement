package com.usermanagement.presenter;

import android.content.Context;
import android.content.Intent;
import com.usermanagement.activity.LoginActivity;
import com.usermanagement.api.ApiClient;
import com.usermanagement.model.SignupUser;
import com.usermanagement.modelresponse.SignupResponse;
import com.usermanagement.utils.ProgressDialogManager;
import com.usermanagement.utils.SharedPreferenceManager;
import com.usermanagement.viewcontractor.SignupContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupPresenter implements SignupContract.Presenter {
    private SignupContract.View view;
    private Context context;
    private ProgressDialogManager progressDialogManager;
    private SharedPreferenceManager preferenceManager;

    public SignupPresenter(SignupContract.View view, Context context) {
        this.view = view;
        this.context = context;
        progressDialogManager = ProgressDialogManager.getInstance(context);
        preferenceManager = new SharedPreferenceManager(context);
    }

    @Override
    public void onRegister(SignupUser user) {
        progressDialogManager.showProgressDialog();
        Call<SignupResponse> call = ApiClient.getInstance().getApi().getSignup(user.getUsername(),user.getEmail(),user.getPassword());
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                progressDialogManager.hideProgressDialog();
                SignupResponse result = response.body();
                if (result.getError().equals("000")){
                    view.onSuccess(result.getMessage());
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }else {
                    view.onError(result.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                progressDialogManager.hideProgressDialog();
                view.onError(t.getMessage());
            }
        });
    }

    @Override
    public void onlocateToSignin() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
