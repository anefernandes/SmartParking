package com.therealbatman.estacionamentointeligente.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Saida {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("placa")
    @Expose
    private String placa;
    @SerializedName("modelo_carro")
    @Expose
    private String modelo_carro;
    @SerializedName("dtSaida")
    @Expose
    private String dtSaida;
    @SerializedName("hrSaida")
    @Expose
    private String hrSaida;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo_carro() {
        return modelo_carro;
    }

    public void setModelo_carro(String modelo_carro) {
        this.modelo_carro = modelo_carro;
    }

    public String getDtSaida() {
        return dtSaida;
    }

    public void setDtSaida(String dtSaida) {
        this.dtSaida = dtSaida;
    }

    public String getHrSaida() {
        return hrSaida;
    }

    public void setHrSaida(String hrSaida) {
        this.hrSaida = hrSaida;
    }
}
