package com.example.rodneytressler.peoplemon.Stages;

import android.app.Application;

import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Riggers.SlideRigger;

/**
 * Created by rodneytressler on 11/7/16.
 */

public class LoginStage extends IndexedStage {
    private final SlideRigger rigger;

    public LoginStage(Application context) {
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public LoginStage() {
        this(PeopleMonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
