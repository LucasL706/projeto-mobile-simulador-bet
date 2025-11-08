package com.puc.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            TimeDao timeDao = db.timeDao();

            // Lista de times que você quer garantir que existam
            String[] nomesTimes = {
                    "Palmeiras", "Sao Paulo", "Santos",
                    "Fluminense", "Vasco", "Botafogo",
                    "Bahia", "Vitoria", "Atletico-MG", "Cruzeiro"
            };

            // Para cada time da lista, verifica se já existe no banco
            for (String nome : nomesTimes) {
                Time existente = timeDao.buscarPorNome(nome);
                if (existente == null) {
                    timeDao.inserir(new Time(nome));
                }
            }
        }).start();

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
                    intent.putExtra("email_usuario", usuario.getEmail());
                    SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("user_id", usuario.getId());
                    editor.putString("user_nome", usuario.getNome());
                    editor.putString("user_email", usuario.getEmail());
                    editor.apply();
                    startActivity(intent);
                    finish();
                }
            });
        }).start();
    }
}
