package com.puc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormLogin extends AppCompatActivity {
    private TextView text_tela_cadastro;
    private Button entrar;
    private EditText edit_email, edit_senha;
    private ProgressBar progressBar;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        inicialComponentes();

        db = AppDatabase.getInstance(this);

        text_tela_cadastro.setOnClickListener(v -> {
            Intent intent = new Intent(FormLogin.this, FormCadastro.class);
            startActivity(intent);
        });

        entrar.setOnClickListener(v -> {
            String email = edit_email.getText().toString().trim();
            String senha = edit_senha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                verificarLogin(email, senha);
            }
        });
    }

    private void inicialComponentes() {
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro);
        entrar = findViewById(R.id.btnEntrar);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void verificarLogin(String email, String senha) {
        progressBar.setVisibility(View.VISIBLE);

        new Thread(() -> {
            Usuario usuario = db.usuarioDao().buscarPorEmail(email);

            runOnUiThread(() -> {
                progressBar.setVisibility(View.INVISIBLE);

                if (usuario == null) {
                    Toast.makeText(FormLogin.this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                } else if (!usuario.getSenha().equals(senha)) {
                    Toast.makeText(FormLogin.this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FormLogin.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FormLogin.this, TelaJogos.class);
                    startActivity(intent);
                    finish();
                }
            });
        }).start();
    }
}
