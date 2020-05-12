package com.therealbatman.estacionamentointeligente.model;

import java.io.Serializable;

public class Estacionamento implements Serializable {
    private int id;

    private String placa;
    private String modelo_carro;
    private String data;
    private String horario;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo_carro;
    }

    public void setModelo(String modelo) {
        this.modelo_carro = modelo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
