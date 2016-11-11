package com.example.rodneytressler.peoplemon.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rodneytressler.peoplemon.MainActivity;
import com.example.rodneytressler.peoplemon.Models.Auth;
import com.example.rodneytressler.peoplemon.Models.ImageLoadedEvent;
import com.example.rodneytressler.peoplemon.Network.RestClient;
import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Stages.MapViewStage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
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
    ImageView imageAppear;

    private Bitmap image;
    private Bitmap decodedByte;

    Date date;
    private ArrayList<Auth> people;
    private String selectedImage;
    public Bitmap scaledImage;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    String encoded;

    public EditProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);



        getStuff();
    }

    @OnClick(R.id.camera_button)
    public void buttonPicture() {
       ((MainActivity)context).getImage();
        ImageLoadedEvent image = new ImageLoadedEvent("fun");
    }

    @OnClick(R.id.save_button)
    public void updateInfo() {


        final String name = editUserName.getText().toString();
        final Auth update = new Auth(name, encoded);
        RestClient restClient = new RestClient();
        restClient.getApiService().updateInfo(update).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    update.setFullName(name);
                    update.setImage("SHAWN");
                    Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
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
                        byte[] decodedString = Base64.decode(dudes.getImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageAppear.setImageBitmap(decodedByte);

                    }
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {

            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelectedImage(ImageLoadedEvent event) {
        selectedImage = event.selectedImage;
        image = BitmapFactory.decodeFile(selectedImage);
        imageAppear.setImageBitmap(image);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

    }



}



