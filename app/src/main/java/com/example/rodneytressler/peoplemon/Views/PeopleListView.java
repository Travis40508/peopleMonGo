package com.example.rodneytressler.peoplemon.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rodneytressler.peoplemon.Adapters.PeopleListAdapter;
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
 * Created by rodneytressler on 11/9/16.
 */

public class PeopleListView extends RelativeLayout {
    private Context context;
    private RestClient restClient;
    private PeopleListAdapter adapter;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    public PeopleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        restClient = new RestClient();

        adapter = new PeopleListAdapter(new ArrayList<Auth>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        loadDudes();

    }

    public void loadDudes() {
        restClient.getApiService().listCaught().enqueue(new Callback<Auth[]>() {
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

            }
        });
    }
}
