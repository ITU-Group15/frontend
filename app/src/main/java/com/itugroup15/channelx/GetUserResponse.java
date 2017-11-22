package com.itugroup15.channelx;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aherka on 22/11/2017.
 */

public class GetUserResponse {

    public class Context {

        @SerializedName("userID")
        @Expose
        private int userID;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("realname")
        @Expose
        private String realname;
        @SerializedName("realsurname")
        @Expose
        private String realsurname;

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getRealsurname() {
            return realsurname;
        }

        public void setRealsurname(String realsurname) {
            this.realsurname = realsurname;
        }

    }

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("context")
    @Expose
    private List<Context> context = null;

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

    public List<Context> getContext() {
        return context;
    }

    public void setContext(List<Context> context) {
        this.context = context;
    }
}