package com.example.francisco.myfirebaseapp;

import com.google.firebase.database.FirebaseDatabase;

public class MyFIrebaseApp extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
