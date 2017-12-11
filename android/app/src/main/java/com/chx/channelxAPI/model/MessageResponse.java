package com.itugroup15.channelxAPI.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by berke on 8.12.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MessageResponse {

    public class Context {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("CreatedAt")
        @Expose
        private String createdAt;
        @SerializedName("UpdatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("DeletedAt")
        @Expose
        private Object deletedAt;
        @SerializedName("channelID")
        @Expose
        private Integer channelID;
        @SerializedName("userID")
        @Expose
        private Integer userID;
        @SerializedName("message")
        @Expose
        private String message;

        public Context(Integer userID, String message) {
            this.userID = userID;
            this.message = message;
        }

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public Integer getChannelID() {
            return channelID;
        }

        public void setChannelID(Integer channelID) {
            this.channelID = channelID;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }


    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("context")
    @Expose
    private List<Context> context = null;

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

    public List<Context> getContext() {
        return context;
    }

    public void setContext(List<Context> context) {
        this.context = context;
    }

}