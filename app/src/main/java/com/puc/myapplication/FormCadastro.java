package com.puc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormCadastro extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha;
    private Button btnCadastrar;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        db = AppDatabase.getInstance(this);

        editNome = findViewById(R.id.edit_nome);
        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(view -> {
            String nome = editNome.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(FormCadastro.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario existente = db.usuarioDao().buscarPorEmail(email);
            if (existente != null) {
                Toast.makeText(FormCadastro.this, "Email já cadastrado!", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario(nome, email, senha);
            db.usuarioDao().inserir(usuario);

            Toast.makeText(FormCadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(FormCadastro.this, FormLogin.class);
            startActivity(intent);
            finish();
        });
    }
}
