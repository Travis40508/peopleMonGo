package com.example.rodneytressler.peoplemon.Stages;

import android.app.Application;

import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Riggers.VerticalSlideRigger;

/**
 * Created by rodneytressler on 11/9/16.
 */

public class PeopleListStage extends IndexedStage {
    private final VerticalSlideRigger rigger;

    public PeopleListStage(Application context) {
        super(RegisterStage.class.getName());
        this.rigger = new VerticalSlideRigger(context);
    }

    public PeopleListStage() {
        this(PeopleMonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.people_list_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}

