package com.therealbatman.estacionamentointeligente.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vagas {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("vagasDisponiveis") //ou depois tentar vagas_disponiveis
    @Expose
    private int vagasDisponiveis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(int vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }
}
