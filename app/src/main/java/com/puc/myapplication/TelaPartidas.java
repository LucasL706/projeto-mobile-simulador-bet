package com.puc.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaPartidas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_partidas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView titulo = findViewById(R.id.tituloAposta);

        // Recebe os nomes vindos da Intent
        String time1 = getIntent().getStringExtra("time1");
        String time2 = getIntent().getStringExtra("time2");

        // Define o texto dinamicamente
        if (time1 != null && time2 != null) {
            titulo.setText(time1 + " vs " + time2);
        } else {
            titulo.setText("Partida não encontrada");
        }

        Button btnApostar1 = findViewById(R.id.btnTimeA);
        Button btnApostar3 = findViewById(R.id.btnTimeB);

        // Atualiza o texto da partida
        if (time1 != null && time2 != null) {
            titulo.setText(time1 + " vs " + time2);

            // Atualiza os textos dos botões
            btnApostar1.setText("Apostar no " + time1);
            btnApostar3.setText("Apostar no " + time2);

        }
    }
}