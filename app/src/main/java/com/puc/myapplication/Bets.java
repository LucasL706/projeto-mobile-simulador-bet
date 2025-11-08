package com.puc.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.List;

public class Bets extends AppCompatActivity {

    private AppDatabase db;
    private int usuarioId; // exemplo, substituir pelo usuário atual
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bets);

        db = AppDatabase.getInstance(this);
        usuarioId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("user_id", -1);
        usuario = db.usuarioDao().buscarPorId(usuarioId);

        carregarApostas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarApostas();
    }


    private void carregarApostas() {
        LinearLayout layoutApostas = findViewById(R.id.layoutApostas);
        layoutApostas.removeAllViews(); // limpa antes de adicionar novamente

        List<Aposta> apostas = db.apostaDao().listarApostasDoUsuario(usuarioId);

        for (Aposta aposta : apostas) {
            CardView card = new CardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 0, 0, 16);
            card.setLayoutParams(cardParams);
            card.setRadius(16);
            card.setCardElevation(8);

            LinearLayout cardLayout = new LinearLayout(this);
            cardLayout.setOrientation(LinearLayout.VERTICAL);
            cardLayout.setPadding(24, 24, 24, 24);

            TextView txtTitulo = new TextView(this);
            txtTitulo.setText(aposta.getTituloPartida());
            txtTitulo.setTextSize(16);
            txtTitulo.setTextColor(Color.BLACK);
            txtTitulo.setPadding(0, 0, 0, 8);

            TextView txtAposta = new TextView(this);
            txtAposta.setText("Aposta: R$" + aposta.getValor() + " em " + aposta.getApostaEm());
            txtAposta.setTextColor(Color.DKGRAY);
            txtAposta.setPadding(0, 0, 0, 8);

            TextView txtStatus = new TextView(this);
            String status = "";
            int color = Color.GRAY;

            switch (aposta.getStatus()) {
                case "simular":
                    status = "🔵 Simular";
                    color = Color.BLUE;
                    break;
                case "acertou":
                    status = "🟢 Vencida";
                    color = Color.GREEN;
                    break;
                case "perdeu":
                    status = "🔴 Perdida";
                    color = Color.RED;
                    break;
            }

            txtStatus.setText("Status: " + status);
            txtStatus.setTextColor(color);

            // Adicionar TextViews no layout do Card
            cardLayout.addView(txtTitulo);
            cardLayout.addView(txtAposta);
            cardLayout.addView(txtStatus);

            // Se for simular, adiciona botão
            if (aposta.getStatus().equals("simular")) {
                Button btnSimular = new Button(this);
                btnSimular.setText("Simular");
                btnSimular.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                btnSimular.setOnClickListener(v -> {
                    boolean acertou = Math.random() < 0.5;

                    if (acertou) {
                        aposta.setStatus("acertou");
                        double ganho = aposta.getValor() * aposta.getOdd();
                        usuario.setSaldo(usuario.getSaldo() + ganho);
                        db.usuarioDao().atualizar(usuario);
                    } else {
                        aposta.setStatus("perdeu");
                    }

                    db.apostaDao().atualizar(aposta);
                    carregarApostas();
                });

                cardLayout.addView(btnSimular);
            }

            card.addView(cardLayout);
            layoutApostas.addView(card);
        }
    }
}