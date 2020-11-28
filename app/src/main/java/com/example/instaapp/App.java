package com.example.instaapp;

import android.app.Application;
import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.applicationID))
                // if defined
                .clientKey(getString(R.string.clientKey))
                .server(getString(R.string.ParseAPI))
                .build()
        );
    }

}
