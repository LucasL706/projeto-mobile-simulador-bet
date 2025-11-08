package com.puc.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "times")
public class Time {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nome;

    public Time(String nome) {
        this.nome = nome;
    }

    // Getters e setters
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
}
