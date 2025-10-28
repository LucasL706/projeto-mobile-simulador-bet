package com.puc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaJogos extends AppCompatActivity {
    TextView perfil, bets, carteira;
    ImageView paraTelaPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_jogos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        perfil = findViewById(R.id.imgPerfil);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaJogos.this, TelaPerfil.class);
                startActivity(intent);
            }
        });
        bets = findViewById(R.id.imgBets);
        bets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaJogos.this, Bets.class);
                startActivity(intent);
            }
        });
        carteira = findViewById(R.id.imgCarteira);
        carteira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaJogos.this, Carteira.class);
                startActivity(intent);
            }
        });
        paraTelaPerfil = findViewById(R.id.icnPerfil);
        paraTelaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaJogos.this, TelaPerfil.class);
                startActivity(intent);
            }
        });
    }
}