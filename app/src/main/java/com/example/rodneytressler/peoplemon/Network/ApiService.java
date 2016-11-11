package com.example.rodneytressler.peoplemon.Network;

import com.example.rodneytressler.peoplemon.Models.Auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rodneytressler on 11/7/16.
 */

public interface ApiService {
    @POST("/api/Account/Register")
    Call<Void> register(@Body Auth account);

    @FormUrlEncoded
    @POST("token")
    Call<Auth> login(@Field("username") String email, @Field("password") String password, @Field("grant_type") String grantType);

    @GET("/v1/User/Nearby")
    Call<Auth[]> getUsers(@Query("radiusInMeters")Integer radius);

    @POST("/v1/User/CheckIn")
    Call<Void> checkIn(@Body Auth check);


    @POST("/v1/User/Catch")
    Call<Void> caught(@Body Auth caughtUser);

    @GET("/v1/User/Caught")
    Call<Auth[]> listCaught();

    @GET("/api/Account/UserInfo")
    Call<Auth> accountInfo();

    @POST("/api/Account/UserInfo")
    Call<Void> updateInfo(@Body Auth updateAuth);
}
