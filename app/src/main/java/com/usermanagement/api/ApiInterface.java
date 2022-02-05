package com.usermanagement.api;

import com.usermanagement.modelresponse.LoginResponse;
import com.usermanagement.modelresponse.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login.php")
    @FormUrlEncoded
    Call<LoginResponse> getLogin(@Field("email") String email, @Field("password") String password);

    @POST("register.php")
    @FormUrlEncoded
    Call<SignupResponse> getSignup(@Field("username") String username, @Field("email") String email, @Field("password") String password);

}
