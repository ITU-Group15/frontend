package com.itugroup15.channelxAPI.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by berke on 10.12.2017.
 */

public class MessageRequest {
    @SerializedName("channelID")
    @Expose
    private Integer channelID;
    @SerializedName("message")
    @Expose
    private String message;

    public MessageRequest(Integer channelID, String message) {
        this.channelID = channelID;
        this.message = message;
    }
    public MessageRequest() {
    }

    public MessageRequest(Integer channelID) {
        this.channelID = channelID;
    }

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
