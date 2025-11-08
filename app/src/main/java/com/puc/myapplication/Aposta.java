package com.puc.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(
        tableName = "apostas",
        foreignKeys = @ForeignKey(
                entity = Usuario.class,
                parentColumns = "id",
                childColumns = "usuarioId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Aposta {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int usuarioId;
    private String tituloPartida;
    private String apostaEm; // "Time A", "Empate", "Time B"
    private double valor;
    private double odd;

    public Aposta(int usuarioId, String tituloPartida, String apostaEm, double valor, double odd) {
        this.usuarioId = usuarioId;
        this.tituloPartida = tituloPartida;
        this.apostaEm = apostaEm;
        this.valor = valor;
        this.odd = odd;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getTituloPartida() { return tituloPartida; }
    public void setTituloPartida(String tituloPartida) { this.tituloPartida = tituloPartida; }

    public String getApostaEm() { return apostaEm; }
    public void setApostaEm(String apostaEm) { this.apostaEm = apostaEm; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public double getOdd() { return odd; }
    public void setOdd(double odd) { this.odd = odd; }
}
