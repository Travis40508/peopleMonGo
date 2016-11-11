package com.example.rodneytressler.peoplemon.Views;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rodneytressler.peoplemon.MainActivity;
import com.example.rodneytressler.peoplemon.Models.Auth;
import com.example.rodneytressler.peoplemon.Network.RestClient;
import com.example.rodneytressler.peoplemon.Network.UserStore;
import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Stages.MapViewStage;
import com.example.rodneytressler.peoplemon.Stages.PeopleListStage;
import com.example.rodneytressler.peoplemon.Stages.nearbyStage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rodneytressler on 11/7/16.
 */


public class MapPageView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

    @Bind(R.id.map_view)
    MapView mapView;
    @Bind(R.id.button3)
    FloatingActionButton listPeopleButton;

    @Bind(R.id.button1)
    FloatingActionButton logOut;





    private LocationManager locManager;
    public GoogleMap mMap;
    private Context context;
    private ArrayList<Auth> people;
    private ArrayList<Auth> names;
    private ArrayList<String> caughtPeople;
    private CountDownTimer test;
    public String name;
    private String realName;
    ArrayAdapter adapter;
    Bitmap please;



    public MapPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
        mapView.onCreate(((MainActivity)getContext()).savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        ((MainActivity)context).showMenuItem(true);


    }

    @OnClick(R.id.button1)
    public void logOut() {
        UserStore.getInstance().setToken(null);
        Flow flow = PeopleMonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new MapViewStage())
                .build(); //pushes on the stack
        flow.setHistory(newHistory, Flow.Direction.BACKWARD);

    }

    @OnClick(R.id.button2)
    public void radar() {
        Flow flow = PeopleMonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new nearbyStage())
                .build(); //pushes on the stack
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
        Toast.makeText(context, "Loading People(Please Be Patient)", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.button3)
    public void showPeopleListView() {
        Toast.makeText(context, "Populating List", Toast.LENGTH_LONG).show();
        Flow flow = PeopleMonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new PeopleListStage())
                .build(); //pushes on the stack
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().isMyLocationButtonEnabled();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        try {
            mMap.setMyLocationEnabled(true);
        } catch(SecurityException e) {

        }
//        mMap.clear();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        LatLng here = new LatLng(37.816257, -82.809496);
//        mMap.addMarker(new MarkerOptions().position(here).title("Your marker is here"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 18));

        Integer radius = 25;
        String authorization = UserStore.getInstance().getToken();
        RestClient restClient = new RestClient();
//        checkIn();
        //Eventually throw in own function that will use handler to call self every few secondsx






    }
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {


            location.setSpeed(1);
            location.getSpeed();
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 20.0f));
            Auth check = new Auth(location.getLongitude(), location.getLatitude());
            RestClient restClient = new RestClient();
            restClient.getApiService().checkIn(check).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()) {
                        checkUser();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            final Circle circle = mMap.addCircle(new CircleOptions().center(loc)
                    .strokeColor(Color.RED).radius(100));

            ValueAnimator valAnim = new ValueAnimator();
            valAnim.setRepeatCount(ValueAnimator.INFINITE);
            valAnim.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
            valAnim.setIntValues(0, 100);
            valAnim.setDuration(5000);
            valAnim.setEvaluator(new IntEvaluator());
            valAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            valAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    circle.setRadius(animatedFraction * 100);
                }
            });
            valAnim.start();
        }

    };


    public void checkUser() {
        Integer radius = 100;
        final RestClient restClient = new RestClient();
        restClient.getApiService().getUsers(radius).enqueue(new Callback<Auth[]>() {
            @Override
            public void onResponse(Call<Auth[]> call, Response<Auth[]> response) {
                if(response.isSuccessful()) {
                    people = new ArrayList<Auth>(Arrays.asList(response.body()));
                    caughtPeople = new ArrayList<String>();
                    mMap.clear();
                    for (Auth dude : people) {
                        name = dude.getId();
                        LatLng loc = new LatLng(dude.getLatitude(), dude.getLongitude());
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.finalmarker);
                        String base64 = dude.getImage();

                        try {
                            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            please = Bitmap.createScaledBitmap(decodedByte, 90, 90, false);
                            BitmapDescriptor cMon = BitmapDescriptorFactory.fromBitmap(please);
                            mMap.addMarker(new MarkerOptions().position(loc).snippet(name).icon(cMon).title(dude.getUserName()));
                        } catch(Exception e) {
                            mMap.addMarker(new MarkerOptions().position(loc).snippet(name).icon(icon).title(dude.getUserName()));
                        }

//                        mMap.addMarker(new MarkerOptions().position(loc).snippet(name).icon(icon).title(dude.getUserName()));




//                        mMap.addMarker(new MarkerOptions().position(loc).snippet(name).icon(icon).title(dude.getUserName()));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {

                                Double latC = marker.getPosition().latitude;
                                Double lngC = marker.getPosition().longitude;
                                LatLng markCircle = new LatLng(latC, lngC);
                                final Circle circle = mMap.addCircle(new CircleOptions().center(markCircle)
                                        .strokeColor(Color.RED).radius(10));
                                ValueAnimator vAnimator = new ValueAnimator();
                                vAnimator.setRepeatCount(1);
                                vAnimator.setRepeatMode(ValueAnimator.REVERSE);  /* PULSE */
                                vAnimator.setIntValues(10, 0);
                                vAnimator.setDuration(500);
                                vAnimator.setEvaluator(new IntEvaluator());
                                vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                                vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        float animatedFraction = valueAnimator.getAnimatedFraction();
                                        circle.setRadius(animatedFraction * 10);
                                    }
                                });
                                vAnimator.start();
                                Toast.makeText(context, "Pokeball thrown...", Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, "You caught " + marker.getTitle() + "!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, marker.getTitle() + " was stored in Bill's PC.", Toast.LENGTH_SHORT).show();
                                marker.remove();
                                Auth caughtDude = new Auth(marker.getSnippet(), 100);
                                RestClient restClient = new RestClient();
                                restClient.getApiService().caught(caughtDude).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()) {
                                            if(response.isSuccessful()) {

                                            } else {
                                                Toast.makeText(context, "CLOSE!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(context, "FAIL!!!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return false;
                            }
                        });
                    }
                } else {
                    Toast.makeText(context, "Not Cool...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Auth[]> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }






}
