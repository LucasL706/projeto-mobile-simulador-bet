package com.puc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TelaJogos extends AppCompatActivity {
    TextView perfil, bets, carteira, userBalance, partida1, partida2;
    ImageView paraTelaPerfil;
    AppDatabase db;
    Usuario usuarioLogado;

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

        // Inicializa banco e componentes
        db = AppDatabase.getInstance(this);
        userBalance = findViewById(R.id.userBalance);

        // Recupera o e-mail do usuário logado
        String emailUsuario = getIntent().getStringExtra("email_usuario");

        if (emailUsuario != null) {
            usuarioLogado = db.usuarioDao().buscarPorEmail(emailUsuario);
            if (usuarioLogado != null) {
                // Formata saldo com duas casas decimais
                DecimalFormat df = new DecimalFormat("R$ #,##0.00");
                userBalance.setText(df.format(usuarioLogado.getSaldo()));
            }
        }

        // Atualiza partidas com times aleatórios
        atualizarPartidasAleatorias();

        // Inicializa botões de navegação
        perfil = findViewById(R.id.imgPerfil);
        perfil.setOnClickListener(view -> startActivity(new Intent(TelaJogos.this, TelaPerfil.class)));

        bets = findViewById(R.id.imgBets);
        bets.setOnClickListener(view -> startActivity(new Intent(TelaJogos.this, Bets.class)));

        carteira = findViewById(R.id.imgCarteira);
        carteira.setOnClickListener(view -> startActivity(new Intent(TelaJogos.this, Carteira.class)));

        paraTelaPerfil = findViewById(R.id.icnPerfil);
        paraTelaPerfil.setOnClickListener(view -> startActivity(new Intent(TelaJogos.this, TelaPerfil.class)));
    }

    private void atualizarPartidasAleatorias() {
        // TextViews das partidas
        TextView partida1 = findViewById(R.id.partida1);
        TextView partida2 = findViewById(R.id.partida2);
        TextView partida3 = findViewById(R.id.partida3);

        // Odds da Partida 1
        TextView odd1a = findViewById(R.id.odd1a);
        TextView odd1b = findViewById(R.id.odd1b);
        TextView odd1c = findViewById(R.id.odd1c);

        // Odds da Partida 2
        TextView odd2a = findViewById(R.id.odd2a);
        TextView odd2b = findViewById(R.id.odd2b);
        TextView odd2c = findViewById(R.id.odd2c);

        // Odds da Partida 3
        TextView odd3a = findViewById(R.id.odd3a);
        TextView odd3b = findViewById(R.id.odd3b);
        TextView odd3c = findViewById(R.id.odd3c);

        List<Time> times = db.timeDao().listarTodos();

        // Precisamos de pelo menos 6 times diferentes
        if (times != null && times.size() >= 6) {
            // Cria uma cópia da lista e embaralha
            List<Time> timesAleatorios = new ArrayList<>(times);
            Collections.shuffle(timesAleatorios, new Random());

            // Seleciona os 6 primeiros (sem repetição)
            Time t1 = timesAleatorios.get(0);
            Time t2 = timesAleatorios.get(1);
            Time t3 = timesAleatorios.get(2);
            Time t4 = timesAleatorios.get(3);
            Time t5 = timesAleatorios.get(4);
            Time t6 = timesAleatorios.get(5);

            // Atualiza textos com confrontos únicos
            partida1.setText(t1.getNome() + " vs " + t2.getNome());
            partida2.setText(t3.getNome() + " vs " + t4.getNome());
            partida3.setText(t5.getNome() + " vs " + t6.getNome());

            // Atualiza odds aleatórias
            Random random = new Random();
            odd1a.setText(gerarOddAleatoria(random));
            odd1b.setText(gerarOddAleatoria(random));
            odd1c.setText(gerarOddAleatoria(random));

            odd2a.setText(gerarOddAleatoria(random));
            odd2b.setText(gerarOddAleatoria(random));
            odd2c.setText(gerarOddAleatoria(random));

            odd3a.setText(gerarOddAleatoria(random));
            odd3b.setText(gerarOddAleatoria(random));
            odd3c.setText(gerarOddAleatoria(random));

        } else {
            Toast.makeText(this, "Cadastre pelo menos 6 times!", Toast.LENGTH_SHORT).show();
        }
    }

    // Gera uma odd entre 1.20 e 3.50 com duas casas decimais
    private String gerarOddAleatoria(Random random) {
        double odd = 1.20 + (3.50 - 1.20) * random.nextDouble();
        return String.format(Locale.US, "%.2f", odd);
    }


}
