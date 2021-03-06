package com.example.rodneytressler.peoplemon.Network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rodneytressler on 10/31/16.
 */

public class SessionRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (com.example.rodneytressler.peoplemon.Network.UserStore.getInstance().getToken() != null) {
            Request.Builder builder = request.newBuilder();
            builder.header("Authorization", "Bearer " +
                        com.example.rodneytressler.peoplemon.Network.UserStore.getInstance().getToken());
            request = builder.build();
        }

        return chain.proceed(request);
    }
}
