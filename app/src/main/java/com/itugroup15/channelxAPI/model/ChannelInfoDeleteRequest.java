package com.itugroup15.channelxAPI.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelInfoDeleteRequest {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("context")
    @Expose
    private Context context;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChannelInfoDeleteRequest() {
    }

    /**
     *
     * @param message
     * @param context
     * @param code
     */
    public ChannelInfoDeleteRequest(String message, Integer code, Context context) {
        super();
        this.message = message;
        this.code = code;
        this.context = context;
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

        @SerializedName("nickname")
        @Expose
        public List<String> nickname = null;
        @SerializedName("startTime")
        @Expose
        private String startTime;
        @SerializedName("endTime")
        @Expose
        private String endTime;
        @SerializedName("availableDays")
        @Expose
        private Object availableDays;
        @SerializedName("channelName")
        @Expose
        private String channelName;
        @SerializedName("owner")
        @Expose
        private String owner;
        @SerializedName("channelID")
        @Expose
        private Integer channelID;

        /**
         * No args constructor for use in serialization
         *
         */
        public Context() {
        }

        /**
         *
         * @param startTime
         * @param channelName
         * @param nickname
         * @param channelID
         * @param owner
         * @param availableDays
         * @param endTime
         */
        public Context(List<String> nickname, String startTime, String endTime, Object availableDays, String channelName, String owner, Integer channelID) {
            super();
            this.nickname = nickname;
            this.startTime = startTime;
            this.endTime = endTime;
            this.availableDays = availableDays;
            this.channelName = channelName;
            this.owner = owner;
            this.channelID = channelID;
        }

        public List<String> getNickname() {
            return nickname;
        }

        public void setNickname(List<String> nickname) {
            this.nickname = nickname;
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

        public Object getAvailableDays() {
            return availableDays;
        }

        public void setAvailableDays(Object availableDays) {
            this.availableDays = availableDays;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public Integer getChannelID() {
            return channelID;
        }

        public void setChannelID(Integer channelID) {
            this.channelID = channelID;
        }

    }

}