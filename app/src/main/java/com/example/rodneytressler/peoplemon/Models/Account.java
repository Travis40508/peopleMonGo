package com.example.rodneytressler.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by rodneytressler on 11/7/16.
 */

public class Account {
    @SerializedName("FullName")
    private String fullName;

    @SerializedName("Password")
    private String password;

    @SerializedName("Email")
    private String email;

    @SerializedName("token")
    private String token;

    @SerializedName("expiration")
    private Date expiration;

    @SerializedName("AvatarBase64")
    private String image = "";

    @SerializedName("ApiKey")
    private String apiKey = "iOSandroid301november2016";

    public Account() {
    }

    public Account(String email, String fullName, String AvatarBase64, String ApiKey, String password) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.apiKey = ApiKey;
        this.image = AvatarBase64;

    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
