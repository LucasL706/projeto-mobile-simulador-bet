package com.puc.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaPerfil extends AppCompatActivity {
    private Button sair;
    private TextView textNomeUser, textEmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_perfil);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referências aos TextViews
        textNomeUser = findViewById(R.id.textNomeUser);
        textEmailUser = findViewById(R.id.textEmailUser);

        // Recupera os dados salvos do usuário
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String nome = prefs.getString("user_nome", "Usuário");
        String email = prefs.getString("user_email", "email@exemplo.com");

        // Atualiza os TextViews com os dados do usuário logado
        textNomeUser.setText(nome);
        textEmailUser.setText(email);

        // Botão de sair
        sair = findViewById(R.id.btnDeslogar);
        sair.setOnClickListener(view -> {
            // Apaga os dados salvos (logout)
            prefs.edit().clear().apply();

            // Volta pra tela de login
            Intent intent = new Intent(TelaPerfil.this, FormLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
