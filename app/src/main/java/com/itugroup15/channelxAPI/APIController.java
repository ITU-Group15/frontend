package com.itugroup15.channelxAPI;

import com.itugroup15.channelxAPI.model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by aherka on 21/11/2017.
 */

public interface APIController {
    @POST("login")
    Call<LoginResponse> login(@Body Login login);

    @POST("register")
    Call<LoginResponse> register(@Body User user);
}
