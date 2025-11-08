package com.puc.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class TelaPartidas extends AppCompatActivity {

    private int usuarioId; // obtido via login
    private Usuario usuario;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_partidas);

        String time1 = getIntent().getStringExtra("time1");
        String time2 = getIntent().getStringExtra("time2");
        String oddTimeA = getIntent().getStringExtra("odd1");
        String oddEmpate = getIntent().getStringExtra("odd2");
        String oddTimeB = getIntent().getStringExtra("odd3");

        db = AppDatabase.getInstance(this);
        usuarioId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("user_id", -1);
        usuario = db.usuarioDao().buscarPorId(usuarioId);

        TextView partida = findViewById(R.id.tituloAposta);
        Button btnA = findViewById(R.id.btnTimeA);
        Button btnEmpate = findViewById(R.id.btnEmpate);
        Button btnB = findViewById(R.id.btnTimeB);
        EditText valorAposta = findViewById(R.id.valorAposta);

        partida.setText(time1 + " vs " + time2);
        btnA.setText("Apostar no " + time1);
        btnB.setText("Apostar no " + time2);

        TextView odd1 = findViewById(R.id.txtOdd1);
        TextView odd2 = findViewById(R.id.txtOdd2);
        TextView odd3 = findViewById(R.id.txtOdd3);

        odd1.setText(oddTimeA);
        odd2.setText(oddEmpate);
        odd3.setText(oddTimeB);

        btnA.setOnClickListener(v -> apostar(time1, time2, time1, Double.parseDouble(oddTimeA), valorAposta));
        btnEmpate.setOnClickListener(v -> apostar(time1, time2, "Empate", Double.parseDouble(oddEmpate), valorAposta));
        btnB.setOnClickListener(v -> apostar(time1, time2, time2, Double.parseDouble(oddTimeB), valorAposta));
    }

    private void apostar(String time1, String time2, String tipoAposta, double odd, EditText campoValor) {
        String valorStr = campoValor.getText().toString().trim();
        if (valorStr.isEmpty()) {
            Toast.makeText(this, "Digite um valor para apostar", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(valorStr);

        if (valor > usuario.getSaldo()) {
            Toast.makeText(this, "Saldo insuficiente!", Toast.LENGTH_SHORT).show();
            return;
        }

        String tituloPartida = time1 + " vs " + time2;
        Aposta nova = new Aposta(usuario.getId(), tituloPartida, tipoAposta, valor, odd);

        new Thread(() -> {
            db.apostaDao().inserir(nova);
            usuario.setSaldo(usuario.getSaldo() - valor);
            db.usuarioDao().atualizar(usuario);

            runOnUiThread(() -> Toast.makeText(this, "Aposta feita com sucesso!", Toast.LENGTH_SHORT).show());
        }).start();
    }
}
