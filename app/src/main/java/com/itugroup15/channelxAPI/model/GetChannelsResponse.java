package com.itugroup15.channelxAPI.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetChannelsResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("context")
    @Expose
    private List<Channel> channels = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Channel> getContext() {
        return channels;
    }

    public void setContext(List<Channel> channels) {
        this.channels = channels;
    }
}
