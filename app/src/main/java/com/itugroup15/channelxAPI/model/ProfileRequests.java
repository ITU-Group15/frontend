package com.itugroup15.channelxAPI.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileRequests {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("realname")
    @Expose
    private String realname;
    @SerializedName("realsurname")
    @Expose
    private String realsurname;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("context")
    @Expose
    private Context context;

    public class Context {
        @SerializedName("user")
        @Expose
        private User user;

        /**
         * No args constructor for use in serialization
         *
         */
        public Context() {
        }

        /**
         *
         * @param user
         */
        public Context(User user) {
            super();
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

    }

    public class User {

        @SerializedName("userID")
        @Expose
        private Integer userID;
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
        @SerializedName("nickname")
        @Expose
        private String nickname;

        /**
         * No args constructor for use in serialization
         */
        public User() {
        }

        /**
         * @param updatedAt
         * @param userID
         * @param phone
         * @param username
         * @param nickname
         * @param createdAt
         * @param realsurname
         * @param realname
         * @param password
         */
        public User(Integer userID, String createdAt, String updatedAt, String username, String password, String phone, String realname, String realsurname, String nickname) {
            super();
            this.userID = userID;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.username = username;
            this.password = password;
            this.phone = phone;
            this.realname = realname;
            this.realsurname = realsurname;
            this.nickname = nickname;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

        /**
     * No args constructor for use in serialization
     *
     */
    public ProfileRequests() {
    }

    /**
     *
     * @param message
     * @param phone
     * @param nickname
     * @param context
     * @param code
     * @param realsurname
     * @param realname
     */
    public ProfileRequests(String message, String nickname, String realname, String realsurname, String phone, Integer code, Context context) {
        super();
        this.message = message;
        this.nickname = nickname;
        this.realname = realname;
        this.realsurname = realsurname;
        this.phone = phone;
        this.code = code;
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

}