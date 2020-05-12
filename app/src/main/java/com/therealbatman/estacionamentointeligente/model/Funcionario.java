package com.therealbatman.estacionamentointeligente.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Funcionario {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("placa_funcionario")
    @Expose
    private String placa_funcionario;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlaca_funcionario() {
        return placa_funcionario;
    }

    public void setPlaca_funcionario(String placa_funcionario) {
        this.placa_funcionario = placa_funcionario;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", placa_funcionario='" + placa_funcionario + '\'' +
                '}';
    }
}
