package com.example.rodneytressler.peoplemon.Stages;

import android.app.Application;

import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Riggers.SlideRigger;

/**
 * Created by rodneytressler on 11/7/16.
 */

public class MapViewStage extends IndexedStage {
    private final SlideRigger rigger;

    public MapViewStage(Application context) {
        super(MapViewStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public MapViewStage() {
        this(PeopleMonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.map_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
