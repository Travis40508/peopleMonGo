package com.example.rodneytressler.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rodneytressler.peoplemon.MainActivity;
import com.example.rodneytressler.peoplemon.Models.Auth;
import com.example.rodneytressler.peoplemon.Network.RestClient;
import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Stages.MapViewStage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rodneytressler on 11/10/16.
 */

public class EditProfileView extends LinearLayout {
    private Context context;
    @Bind(R.id.edit_user_name)
    EditText editUserName;

    @Bind(R.id.save_button)
    Button saveButton;

    @Bind(R.id.camera_button)
    Button cameraButton;

    @Bind(R.id.imageView)
    ImageView imageView;

    Date date;
    private ArrayList<Auth> people;

    public EditProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        getStuff();
    }

    @OnClick(R.id.camera_button)
    public void buttonPicture() {
       ((MainActivity)context).getImage();
    }

    @OnClick(R.id.save_button)
    public void updateInfo() {
        final String name = editUserName.getText().toString();
        final Auth update = new Auth(name, "");
        RestClient restClient = new RestClient();
        restClient.getApiService().updateInfo(update).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(context, "Information Updated", Toast.LENGTH_SHORT).show();
                    update.setFullName(name);
                    Flow flow = PeopleMonApplication.getMainFlow();
                    History newHistory = History.single(new MapViewStage());
                    flow.setHistory(newHistory, Flow.Direction.BACKWARD);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void getStuff() {
        RestClient restClient = new RestClient();
        restClient.getApiService().accountInfo().enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if(response.isSuccessful()) {
                    people = new ArrayList<Auth>(Arrays.asList(response.body()));
                    for(Auth dudes : people) {
                        String test = dudes.getFullName();
                        editUserName.setText(test);

                    }
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {

            }
        });
    }


}
