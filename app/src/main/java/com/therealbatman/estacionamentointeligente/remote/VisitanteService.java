package com.therealbatman.estacionamentointeligente.remote;

import com.therealbatman.estacionamentointeligente.model.Visitante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VisitanteService {
    @GET("visitante/{placa}")
    Call<List<String>> getVisitanteName(@Path("placa") String placa);

    @POST("visitante/add")
    Call<Visitante> addVisitante(@Body Visitante visitante);
}
