package com.puc.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Carteira extends AppCompatActivity {

    private TextView txtSaldo;
    private EditText txtQuantidade;
    private Button btnAddSaldo, btnRetirar;

    private int usuarioId; // obtido via login
    private Usuario usuario;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);

        // Edge to edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Views
        txtSaldo = findViewById(R.id.txtSaldo);
        txtQuantidade = findViewById(R.id.txtQuantidade);
        btnAddSaldo = findViewById(R.id.btnAddSaldo);
        btnRetirar = findViewById(R.id.btnRetirar);

        db = AppDatabase.getInstance(this);
        usuarioId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("user_id", -1);
        usuario = db.usuarioDao().buscarPorId(usuarioId);
        if (usuario == null) {
            Toast.makeText(this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtSaldo.setText("" + usuario.getSaldo());

        btnAddSaldo.setOnClickListener(v -> {
            String valorStr = txtQuantidade.getText().toString().trim();
            if (valorStr.isEmpty()) return;
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) return;

            usuario.setSaldo(usuario.getSaldo() + valor);
            db.usuarioDao().atualizar(usuario);
            atualizarTxtSaldo();
            txtQuantidade.setText("");
            Toast.makeText(this, "Saldo adicionado!", Toast.LENGTH_SHORT).show();
        });

        btnRetirar.setOnClickListener(v -> {
            String valorStr = txtQuantidade.getText().toString().trim();
            if (valorStr.isEmpty()) return;
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) return;

            if (valor > usuario.getSaldo()) {
                Toast.makeText(this, "Saldo insuficiente!", Toast.LENGTH_SHORT).show();
                return;
            }

            usuario.setSaldo(usuario.getSaldo() - valor);
            db.usuarioDao().atualizar(usuario);
            atualizarTxtSaldo();
            txtQuantidade.setText("");
            Toast.makeText(this, "Saldo retirado!", Toast.LENGTH_SHORT).show();
        });
    }

    private void atualizarTxtSaldo() {
        txtSaldo.setText(String.format("R$%.2f", usuario.getSaldo()));
    }
}
