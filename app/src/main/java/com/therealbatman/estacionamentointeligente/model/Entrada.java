package com.therealbatman.estacionamentointeligente.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entrada {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("placa")
    @Expose
    private String placa;
    @SerializedName("modelo_carro")
    @Expose
    private String modelo_carro;
    @SerializedName("dtEntrada")
    @Expose
    private String dtEntrada;
    @SerializedName("hrEntrada")
    @Expose
    private String hrEntrada;

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

    public String getDtEntrada() {
        return dtEntrada;
    }

    public void setDtEntrada(String dtEntrada) {
        this.dtEntrada = dtEntrada;
    }

    public String getHrEntrada() {
        return hrEntrada;
    }

    public void setHrEntrada(String hrEntrada) {
        this.hrEntrada = hrEntrada;
    }
}
