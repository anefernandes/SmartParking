package com.therealbatman.estacionamentointeligente.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtils {

    Retrofit retrofit;

    public APIUtils() {
    }

    public Retrofit getAdapter() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.78:8080/") // //http://192.168.1.78:8080/
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}