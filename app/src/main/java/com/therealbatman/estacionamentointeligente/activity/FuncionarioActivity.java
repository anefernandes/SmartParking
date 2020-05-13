package com.therealbatman.estacionamentointeligente.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.therealbatman.estacionamentointeligente.R;
import com.therealbatman.estacionamentointeligente.remote.APIUtils;
import com.therealbatman.estacionamentointeligente.remote.VagasService;

import java.util.List;

//Classe para mostrar a quantidade de vagas disponível
public class FuncionarioActivity extends AppCompatActivity {

    private TextView vagas;
    public List<Integer> quantidade_vagas;
    private VagasService vagasService;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);
        vagas = findViewById(R.id.tvVagas);
        //retrofit
        retrofit = new APIUtils().getAdapter();
        vagasService = retrofit.create(VagasService.class);
        exibeVagas();

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
                exibeVagas();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    public void exibeVagas(){
        final Call<List<Integer>> vagasDisponiveis = vagasService.getTotal();
        vagasDisponiveis.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if(response.isSuccessful()) {
                    quantidade_vagas = response.body();
                    vagas.setText(quantidade_vagas.toString().replace('[', ' ').replace(']', ' ').trim());
                    System.out.println(quantidade_vagas);
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }
}
