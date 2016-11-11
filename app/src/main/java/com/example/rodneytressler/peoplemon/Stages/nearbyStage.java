package com.example.rodneytressler.peoplemon.Stages;

import android.app.Application;

import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Riggers.SlideRigger;

/**
 * Created by rodneytressler on 11/11/16.
 */

public class nearbyStage extends IndexedStage {
    private final SlideRigger rigger;

    public nearbyStage(Application context) {
        super(MapViewStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public nearbyStage() {
        this(PeopleMonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.nearby_activity;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}

