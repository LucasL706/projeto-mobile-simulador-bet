package com.puc.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ApostaDao {
    @Insert
    void inserir(Aposta aposta);

    @Query("SELECT * FROM apostas WHERE usuarioId = :usuarioId")
    List<Aposta> listarApostasDoUsuario(int usuarioId);

    @Update
    void atualizar(Aposta aposta);
}
