package com.example.rodneytressler.peoplemon.Network;

import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rodneytressler on 10/31/16.
 */

public class RestClient {
    private ApiService apiService;

    public RestClient() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        Gson gson = builder.create(); //Builder is used to set a date format.

        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY); //Tells interceptor to print body of response to log.

        OkHttpClient OkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//gives it 10 seconds before timing out to hear back from server.
                .addInterceptor(new com.example.rodneytressler.peoplemon.Network.SessionRequestInterceptor())
                .addInterceptor(log) // while you're sending and receiving information, use this to tell you what to print to console.
                .build(); //behaves like a small web browser. connects to the server, sends information and retrieves it.

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(PeopleMonApplication.API_BASE_URL) //every URL we're calling will start with base URL.
                .client(OkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)) //this will use Gson to convert Json to our classes.
                .build();//builds it all together.

        apiService = restAdapter.create(ApiService.class);//says all of our gets and posts will be defined in API services class.
    }

    public ApiService getApiService() {
        return apiService;
    }
}
