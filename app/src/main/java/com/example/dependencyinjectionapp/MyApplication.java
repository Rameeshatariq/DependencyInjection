package com.example.dependencyinjectionapp;


import android.app.Application;

import androidx.annotation.UiThread;

import com.example.dependencyinjectionapp.dependencyInjection.CompositionRoot;

import java.util.Stack;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {


    private CompositionRoot compositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        compositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot(){
        return compositionRoot;
    }
}
