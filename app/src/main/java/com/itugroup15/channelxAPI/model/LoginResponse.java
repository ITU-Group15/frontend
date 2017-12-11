package com.itugroup15.channelxAPI.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    public class Context {

        @SerializedName("jwtToken")
        @Expose
        private String jwtToken;
        @SerializedName("userID")
        @Expose
        private int userID;

        public String getJwtToken() {
            return jwtToken;
        }

        public void setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
        }


        public void setUserID(int userID) {
            this.userID = userID;
        }

        public int getUserID() {

            return userID;
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
    private Context context;

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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
