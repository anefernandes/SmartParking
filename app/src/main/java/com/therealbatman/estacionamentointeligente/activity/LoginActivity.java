package com.therealbatman.estacionamentointeligente.activity;

/**
 * Created by anefernandes
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.therealbatman.estacionamentointeligente.R;

public class LoginActivity extends AppCompatActivity {

    private EditText textUsuario, textSenha;
    private Button btLogin;
    private String usuario, senha;
    private TextView cadastro;

    String userF = "func", senhaF = "12345", userE = "admin", senhaE = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textUsuario = findViewById(R.id.username);
        textSenha = findViewById(R.id.password);
        btLogin = findViewById(R.id.btLogin);
        cadastro = findViewById(R.id.tvCadastro);

        //criar o login de botão e armazenar as variaveis com valores admin.

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((textUsuario.getText().toString().equals(userE)) && (textSenha.getText().toString().equals(senhaE))){
                    Intent intent = new Intent(getApplicationContext(), EstacionamentoActivity.class);
                    startActivity(intent);
                }
                else if ((textUsuario.getText().toString().equals(userF)) && (textSenha.getText().toString().equals(senhaF))){
                    Intent intent = new Intent(getApplicationContext(), FuncionarioActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Usuário ou senha incorreta.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void Formulario(View view){
        Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(intent);
    }
}
