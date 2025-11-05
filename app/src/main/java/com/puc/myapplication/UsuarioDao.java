package com.puc.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.puc.myapplication.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    void inserir(Usuario usuario);

    @Query("SELECT * FROM usuarios")
    List<Usuario> listarTodos();

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    Usuario buscarPorEmail(String email);

    @Delete
    void deletar(Usuario usuario);
}
