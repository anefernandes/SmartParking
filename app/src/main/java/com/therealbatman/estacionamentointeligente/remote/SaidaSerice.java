package com.therealbatman.estacionamentointeligente.remote;

import com.therealbatman.estacionamentointeligente.model.Saida;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SaidaSerice {
    @GET("saida/")
    Call<List<Saida>> getSaida();

    @POST("saida/add")
    Call<Saida> addSaida(@Body Saida saida);

}
