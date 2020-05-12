package com.therealbatman.estacionamentointeligente.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FuncionarioService {
    @GET("funcionario/{placa}")
    Call<List<String>> getFuncionarioName(@Path("placa") String placa_funcionario);
}
