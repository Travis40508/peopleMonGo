package com.example.rodneytressler.peoplemon;

import android.app.Application;

import com.example.rodneytressler.peoplemon.Stages.MapViewStage;

import flow.Flow;
import flow.History;

/**
 * Created by rodneytressler on 11/7/16.
 */

public class PeopleMonApplication extends Application {
    public static PeopleMonApplication application;
    public final Flow mainFlow =
            new Flow(History.single(new MapViewStage())); //flow works off of stack information which are in history. stage calls xml file which calls java file. .single means only one thing in this History.

    public static final String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net:443/";

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static PeopleMonApplication getInstance() {
        return application;
    }

    public static Flow getMainFlow() {
        return getInstance().mainFlow;
    }

}

