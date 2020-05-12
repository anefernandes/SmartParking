package com.therealbatman.estacionamentointeligente.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Visitante {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("cpf")
    @Expose
    private String cpf;
    @SerializedName("placa_visitante")
    @Expose
    private String placa_visitante;
    @SerializedName("celular")
    @Expose
    private String celular;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPlaca_visitante() {
        return placa_visitante;
    }

    public void setPlaca_visitante(String placa_visitante) {
        this.placa_visitante = placa_visitante;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Override
    public String toString() {
        return "Visitante{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", placa_visitante='" + placa_visitante + '\'' +
                ", celular='" + celular + '\'' +
                '}';
    }
}
