package com.example.rodneytressler.peoplemon.Stages;

import android.app.Application;

import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Riggers.VerticalSlideRigger;

/**
 * Created by rodneytressler on 11/7/16.
 */

public class RegisterStage extends IndexedStage {
    private final VerticalSlideRigger rigger;

    public RegisterStage(Application context) {
        super(RegisterStage.class.getName());
        this.rigger = new VerticalSlideRigger(context);
    }

    public RegisterStage() {
        this(PeopleMonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.register_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
