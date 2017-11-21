package com.itugroup15.channelxAPI.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aherka on 21/11/2017.
 */

public class User {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("realname")
    @Expose
    private String realname;
    @SerializedName("realsurname")
    @Expose
    private String realsurname;
    @SerializedName("phone")
    @Expose
    private String phone;

    public User(String username, String password, String realname, String realsurname, String phone) {
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.realsurname = realsurname;
        this.phone = phone;
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

}
