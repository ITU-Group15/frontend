package com.itugroup15.channelxAPI.model;

import com.google.gson.annotations.SerializedName;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class ChannelCreateRequest {

    @SerializedName("channelName")
    @Expose

    private String channelName;
    @SerializedName("isAlive")
    @Expose
    private Boolean isAlive;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("availableDays")
    @Expose
    private List<String> availableDays = null;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("context")
    @Expose
    private Context context;

    public ChannelCreateRequest(String channelName, Boolean isAlive, List<String> availableDays, String startTime, String endTime) {
        this.channelName = channelName;
        this.isAlive = isAlive;
        this.availableDays = availableDays;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(Boolean isAlive) {
        this.isAlive = isAlive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class Context {
    }

}