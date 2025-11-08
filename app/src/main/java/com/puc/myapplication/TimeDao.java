package com.puc.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TimeDao {

    @Insert
    void inserir(Time time);

    @Query("SELECT * FROM times WHERE nome = :nome LIMIT 1")
    Time buscarPorNome(String nome);

    @Query("SELECT * FROM times")
    List<Time> listarTodos();
}
