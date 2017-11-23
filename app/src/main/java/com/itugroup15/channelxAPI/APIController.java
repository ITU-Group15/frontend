package com.itugroup15.channelxAPI;

import com.itugroup15.channelxAPI.model.GetUserResponse;
import com.itugroup15.channelxAPI.model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIController {
    @POST("login")
    Call<LoginResponse> login(@Body Login login);

    @POST("register")
    Call<LoginResponse> register(@Body User user);

    @GET("getusers")
    Call<GetUserResponse> getUsers(@Header("Authorization") String authHeader);
}
