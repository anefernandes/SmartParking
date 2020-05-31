package com.therealbatman.estacionamentointeligente.activity;
/**
 * Created by anefernandes
 */
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.therealbatman.estacionamentointeligente.R;
import com.therealbatman.estacionamentointeligente.model.Entrada;
import com.therealbatman.estacionamentointeligente.model.Estacionamento;
import com.therealbatman.estacionamentointeligente.model.Funcionario;
import com.therealbatman.estacionamentointeligente.model.Saida;
import com.therealbatman.estacionamentointeligente.model.Vagas;
import com.therealbatman.estacionamentointeligente.remote.APIUtils;
import com.therealbatman.estacionamentointeligente.remote.EntradaService;
import com.therealbatman.estacionamentointeligente.remote.FuncionarioService;
import com.therealbatman.estacionamentointeligente.remote.SaidaSerice;
import com.therealbatman.estacionamentointeligente.remote.VagasService;
import com.therealbatman.estacionamentointeligente.remote.VisitanteService;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EstacionamentoActivity extends AppCompatActivity {

    private Button captureImageBT, detectTextBT;
    private ImageView imageView;
    private Bitmap imageBitmap;
    private TextView tvVision, tvModel, tvData, tvHora, tvNome;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private LocalDateTime agora = LocalDateTime.now();
    private String placa, modelo, data, hora;
    public List<String> nomeFuncionario, nomeVisitante;
    public int total = 15;
    private boolean key = false;
    FirebaseVisionImageLabeler labeler;
    //retrofit
    private EntradaService entradaService;
    private SaidaSerice saidaSerice;
    FuncionarioService funcionarioService;
    VisitanteService visitanteService;
    VagasService vagasService; //vagas pra utilizar
    Retrofit retrofit;
    //resposta icone
    private ImageView imResposta, imResposta1, imResposta2, imResposta3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //icone

        //botões
        captureImageBT = findViewById(R.id.capture_image);
        detectTextBT = findViewById(R.id.detect_text_image);
        imageView = findViewById(R.id.image_view);
        tvVision = findViewById(R.id.text_vision);
        tvModel = findViewById(R.id.text_model);
        tvData = findViewById(R.id.text_data);
        tvHora = findViewById(R.id.text_hora);
        //retrofit
        tvNome = findViewById(R.id.text_nome);
        retrofit = new APIUtils().getAdapter();
        entradaService = retrofit.create(EntradaService.class);
        saidaSerice = retrofit.create(SaidaSerice.class);
        funcionarioService = retrofit.create(FuncionarioService.class);
        visitanteService = retrofit.create(VisitanteService.class);
        //classe vagas
        vagasService = retrofit.create(VagasService.class);
        //resposta icone
        imResposta = findViewById(R.id.iconresposta);
        imResposta1 = findViewById(R.id.iconresposta1);
        imResposta2 = findViewById(R.id.iconresposta2);
        imResposta3 = findViewById(R.id.iconresposta3);


        //botão
        captureImageBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                tvNome.setText("");
                tvVision.setText("");
                tvModel.setText("");
                tvData.setText("");
                tvHora.setText("");
            }
        });

        detectTextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = true;
                try {
                    detectTextFromImage();
                    detectModel();
                    detectData();
                } catch (Exception e) {
                    Toast.makeText(EstacionamentoActivity.this, "Capture a imagem primeiro.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //capturar imagem da camera
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    //text recognation
    public void detectTextFromImage() {
        //caso não funcionar, colocar a variável bitmap fora do método, ou chamar a variável do método aqui ou colocar esse método dentro do que contem a bitmap.
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap); //passa a imagem para o firebase
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {

                displayTextFromImage(firebaseVisionText);
                //criado para por imagem
                if(firebaseVisionText.equals(null)){
                    imResposta.setImageResource(R.drawable.ic_erro);
                }
                else {
                    imResposta.setImageResource(R.drawable.ic_aprovado);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EstacionamentoActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Erro: ", e.getMessage());
            }
        });
    }

    public void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        //Instanciar o banco de dados aqui.
        List<FirebaseVisionText.TextBlock> blockList = firebaseVisionText.getTextBlocks();
        if (blockList.size() == 0){
            Toast.makeText(this, "O Texto na imagem não foi detectado.", Toast.LENGTH_SHORT).show();
        }
        else {
            for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()){
                String textPlaca = block.getText();
                placa = textPlaca; //placa a ser pega
                tvVision.setText("Placa: " + textPlaca);

            }
        }
        getPlacaF(placa);
        getNomes(placa);
    }

    //modelo treinado
    private void detectModel() {
        //vou precisar instanciar o banco de dados aqui
        FirebaseAutoMLLocalModel localModel = new FirebaseAutoMLLocalModel.Builder().setAssetFilePath("automl/manifest.json").build();

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);

        try {
            FirebaseVisionOnDeviceAutoMLImageLabelerOptions options =
                    new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel)
                            .setConfidenceThreshold(0.5f)  // Evaluate your model in the Firebase console
                            // to determine an appropriate value.
                            .build();
            labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
        } catch (FirebaseMLException e) {
            Toast.makeText(this,  "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        labeler.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                        for (FirebaseVisionImageLabel label: labels) {
                            String textModelo = label.getText();
                            modelo = textModelo; //modelo para ser pego
                            float confidence = label.getConfidence();
                                tvModel.setText("Modelo: " + textModelo);

                                //adicionando imagem
                                if(textModelo.equals(null)){
                                    imResposta1.setImageResource(R.drawable.ic_erro);
                                }
                                else{
                                    imResposta1.setImageResource(R.drawable.ic_aprovado);
                                }
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EstacionamentoActivity.this,  "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //data E hora
    private void detectData(){
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = formatterData.format(agora);
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = formatterHora.format(agora);
        data = dataFormatada; //data para pegar
        hora = horaFormatada; //hora para pegar

        tvData.setText("Data: " + dataFormatada);
        tvHora.setText("Hora: " + horaFormatada);

        //imagem no icone
        imResposta2.setImageResource(R.drawable.ic_aprovado);
        imResposta3.setImageResource(R.drawable.ic_aprovado);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_repetir:
                imageView.setImageBitmap(null);
                //Funcionario ou visitante
                tvNome.setText(null);
                tvVision.setText(null);
                tvModel.setText(null);
                tvData.setText(null);
                tvHora.setText(null);
                break;

            case R.id.action_entrada:
                if(key == true){
                //salvar os dados completo.
                Entrada e = new Entrada();
                e.setPlaca(placa);
                e.setModelo_carro(modelo);
                e.setDtEntrada(data);
                e.setHrEntrada(hora);
                total = total - 1;
                addEntrada(e);
                //passar os dados da activity aqui
                Vagas v_Menos = new Vagas();
                v_Menos.setId(1);
                v_Menos.setVagasDisponiveis(total);
                //metodo retrofit
                getVagas(v_Menos);}
                else{
                    Toast.makeText(EstacionamentoActivity.this, "Detecte o texto primeiro.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.action_saida:
                if(key == true) {
                    //salvar os dados de saida
                    Saida s = new Saida();
                    s.setPlaca(placa);
                    s.setModelo_carro(modelo);
                    s.setDtSaida(data);
                    s.setHrSaida(hora);
                    total = total + 1;
                    addSaida(s);
                    //passar os dados da activity aqui
                    Vagas v_Mais = new Vagas();
                    v_Mais.setId(1);
                    v_Mais.setVagasDisponiveis(total);
                    //metodo retrofit
                    getVagas(v_Mais);
                } else {
                    Toast.makeText(EstacionamentoActivity.this, "Detecte o texto primeiro.", Toast.LENGTH_SHORT).show();
                }

                break;
        }


        return super.onOptionsItemSelected(item);
    }


    //adicionar entrada no banco
    private void addEntrada(Entrada e) {
        Call<Entrada> callEntrada = entradaService.addEntrada(e);
        callEntrada.enqueue(new Callback<Entrada>() {
            @Override
            public void onResponse(Call<Entrada> call, Response<Entrada> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EstacionamentoActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Entrada> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    //adicionar saida no banco
    private void addSaida(Saida s) {
        Call<Saida> callSaida = saidaSerice.addSaida(s);
        callSaida.enqueue(new Callback<Saida>() {
            @Override
            public void onResponse(Call<Saida> call, Response<Saida> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EstacionamentoActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Saida> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    //visualizar nomes em tela
    private void getPlacaF(String placaF) {
        Call<List<String>> funcionarioNome = funcionarioService.getFuncionarioName(placaF);

        funcionarioNome.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()) {
                    nomeFuncionario = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
        System.out.println(nomeFuncionario);
    }

    private void getNomes(String placaV){
        Call <List<String>> visitanteNome = visitanteService.getVisitanteName(placaV);
        visitanteNome.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    nomeVisitante = response.body();
                    System.out.println(nomeFuncionario);
                    System.out.println(nomeVisitante);
                }
                if ((nomeVisitante.isEmpty()) && (nomeFuncionario.isEmpty())) {
                    tvNome.setText("Funcionário não encontrado.");
                }
                else if (nomeVisitante.isEmpty()) {
                    tvNome.setText("Funcionário: " + nomeFuncionario.toString().replace('[', ' ').replace(']', ' ').trim());
                }
                else{
                    tvNome.setText("Visitante: " + nomeVisitante.toString().replace('[', ' ').replace(']', ' ').trim());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    //atualizar vagas
    private void getVagas(Vagas v){
        Call<Vagas> callVagas = vagasService.atualizaVagas(v);
        callVagas.enqueue(new Callback<Vagas>() {
            @Override
            public void onResponse(Call<Vagas> call, Response<Vagas> response) {
                if(response.isSuccessful()){
                    Vagas vagasResponse = response.body();
                    System.out.println(vagasResponse);
                }
            }

            @Override
            public void onFailure(Call<Vagas> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

}