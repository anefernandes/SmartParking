package com.therealbatman.estacionamentointeligente.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.therealbatman.estacionamentointeligente.R;

//Classe para mostrar a quantidade de vagas disponível
public class FuncionarioActivity extends AppCompatActivity {
    EstacionamentoActivity estacionamento;
    private TextView vagas, texto;
    public int totalE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);
        texto = findViewById(R.id.tvQtvagas);
        vagas = findViewById(R.id.tvVagas);
        Bundle extras = getIntent().getExtras();

    }

    //botão atualizar dentro da activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.funcionario_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_atualizar:
                    vagas.setText(estacionamento.total);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
