package com.itugroup15.channelxAPI.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinChannel {
    @SerializedName("channelID")
    @Expose
    private int channelID;

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }
}
