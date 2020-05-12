package com.therealbatman.estacionamentointeligente.remote;

import com.therealbatman.estacionamentointeligente.model.Entrada;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EntradaService {
    @GET("entrada/")
    Call<List<Entrada>> getEntrada();

    @POST("entrada/add")
    Call<Entrada> addEntrada(@Body Entrada entrada);

}
