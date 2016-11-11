package com.example.rodneytressler.peoplemon.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rodneytressler.peoplemon.Adapters.RadarAdapter;
import com.example.rodneytressler.peoplemon.Models.Auth;
import com.example.rodneytressler.peoplemon.Network.RestClient;
import com.example.rodneytressler.peoplemon.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rodneytressler on 11/11/16.
 */

public class NearbyView extends LinearLayout {
    private Context context;
    private RestClient restClient;
    private RadarAdapter adapter;

    @Bind(R.id.recycler_view_radar)
    RecyclerView radar;

    public NearbyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        restClient = new RestClient();

        adapter = new RadarAdapter(new ArrayList<Auth>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        radar.setLayoutManager(linearLayoutManager);
        radar.setAdapter(adapter);

        loadRadar();
    }

    public void loadRadar() {
        Integer radius = 100;
        RestClient restClient = new RestClient();
        restClient.getApiService().getUsers(radius).enqueue(new Callback<Auth[]>() {
            @Override
            public void onResponse(Call<Auth[]> call, Response<Auth[]> response) {
                if(response.isSuccessful()) {
                    adapter.caughtDudes = new ArrayList<Auth>(Arrays.asList(response.body()));
                    for(Auth caught : adapter.caughtDudes) {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(context, "Nope", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Auth[]> call, Throwable t) {
                Toast.makeText(context, "Didn't connect", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
