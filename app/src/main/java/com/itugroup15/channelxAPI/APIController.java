package com.itugroup15.channelxAPI;

import com.itugroup15.channelxAPI.model.GetUserResponse;
import com.itugroup15.channelxAPI.model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIController {
    @POST("login")
    Call<LoginResponse> login(@Body Login login);

    @POST("register")
    Call<LoginResponse> register(@Body User user);

    @GET("getusers")
    Call<GetUserResponse> getUsers(@Header("Authorization") String authHeader);

    @GET("channels")
    Call<GetChannelsResponse> getChannels(@Header("Authorization") String authHeader);

    @POST("search")
    Call<GetChannelsResponse> searchChannels(@Header("Authorization") String authHeader, @Body SearchQuery channelName);

    @POST("join")
    Call<GetChannelsResponse> joinChannel(@Header("Authorization") String authHeader, @Body JoinChannel channelID);


    @POST("send")
    Call<MessageResponse> sendMessage(
             @Header("Authorization") String authHeader
            ,@Body MessageRequest messageRequest);

    @POST("getmessages")
    Call<MessageResponse> getMessages(
            @Header("Authorization") String authHeader
            ,@Body MessageRequest messageRequest);

    @POST("create")
    Call<ChannelCreateRequest> createChannel(
            @Header("Authorization") String authHeader
            ,@Body ChannelCreateRequest channelCreateRequest
    );

    @GET("channel/{id}")
    Call<ChannelInfoDeleteRequest> getChannelInfo(
            @Header("Authorization") String authHeader
            ,@Path("id") int ChannelID
    );

    @GET("delete/{id}")
    Call<ChannelInfoDeleteRequest> deleteChannel(
            @Header("Authorization") String authHeader
            ,@Path("id") int ChannelID
    );

    @GET("/profile")
    Call<ProfileRequests> getProfile(
            @Header("Authorization") String authHeader
    );

    @POST("/changeprofile")
    Call<ProfileRequests> updateProfile(
            @Header("Authorization") String authHeader,
            @Body ProfileRequests profileRequests
    );

}
