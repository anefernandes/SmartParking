package com.therealbatman.estacionamentointeligente.remote;

import com.therealbatman.estacionamentointeligente.model.Vagas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface VagasService {
    @GET("vagas/total")
    Call<List<Integer>> getTotal();

    @PUT("atualiza/")
    Call<Vagas> atualizaVagas(@Body Vagas vagas);
}
