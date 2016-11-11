package com.example.rodneytressler.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by rodneytressler on 11/7/16.
 */

public class Auth {
    @SerializedName("grant_type")
    String grantType;


    @SerializedName("access_token")
    private String accessToken;

    @SerializedName(".expires")
    private Date expires;

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
    private String image;

    @SerializedName("ApiKey")
    private String apiKey = "iOSandroid301november2016";

    @SerializedName("Longitude")
    private double longitude;

    @SerializedName("Latitude")
    private double latitude;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("CaughtUserId")
    String caughtId;

    @SerializedName("RadiusInMeters")
    int radius;

    @SerializedName("UserId")
    private String id;


    public Auth() {

    }

    public Auth(String grantType, String userName, String password) {
        this.grantType = grantType;
        this.email = userName;
        this.password = password;
    }

    public Auth(String email, String fullName, String AvatarBase64, String ApiKey, String password) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.apiKey = ApiKey;
        this.image = AvatarBase64;

    }

    public Auth(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Auth(String fullName, double longitude, double latitude) {
        this.fullName = fullName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Auth(String caughtId, int radius) {
        this.caughtId = caughtId;
        this.radius = radius;
    }

    public Auth(String name, String image) {
        this.fullName = name;
        this.image = image;
    }

    public Auth(String fullName) {
        this.fullName = fullName;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCaughtId() {
        return caughtId;
    }

    public void setCaughtId(String caughtId) {
        this.caughtId = caughtId;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
