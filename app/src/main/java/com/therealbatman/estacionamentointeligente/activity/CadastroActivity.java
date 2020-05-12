package com.therealbatman.estacionamentointeligente.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.therealbatman.estacionamentointeligente.R;
import com.therealbatman.estacionamentointeligente.model.Visitante;
import com.therealbatman.estacionamentointeligente.remote.APIUtils;
import com.therealbatman.estacionamentointeligente.remote.VisitanteService;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etCpf, etPlaca, etCelular;
    private Button cadastrar;
    //retrofit
    private VisitanteService visitanteService;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = findViewById(R.id.etNome);
        etCpf = findViewById(R.id.etCpf);
        etPlaca = findViewById(R.id.etPlaca);
        etCelular = findViewById(R.id.etCelular);
        cadastrar = findViewById(R.id.btCadastrar);
        //retrofit
        retrofit = new APIUtils().getAdapter();
        visitanteService = retrofit.create(VisitanteService.class);


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visitante vi = new Visitante();
                vi.setNome(etNome.getText().toString());
                vi.setCpf(etCpf.getText().toString());
                vi.setPlaca_visitante(etPlaca.getText().toString());
                vi.setCelular(etCelular.getText().toString());
                addFuncionario(vi);
            }
        });

    }

    private void addFuncionario(Visitante visitante) {
        Call<Visitante> callFuncionario = visitanteService.addVisitante(visitante);
        callFuncionario.enqueue(new Callback<Visitante>() {
            @Override
            public void onResponse(Call<Visitante> call, Response<Visitante> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Visitante> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
