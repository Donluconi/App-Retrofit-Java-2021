package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.retrofit.api.CEPService;
import com.example.retrofit.api.DataService;
import com.example.retrofit.model.CEP;
import com.example.retrofit.model.Foto;
import com.example.retrofit.model.Postagem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar, botaoExecutar;
    private TextView textoResultado;
    private Retrofit retrofit;
    private List<Foto> listaFotos = new ArrayList<>();
    private DataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        botaoRecuperar = findViewById(R.id.buttonRecuperar);
        botaoExecutar = findViewById(R.id.buttonExecutar);
        textoResultado = findViewById(R.id.textResultado);

        //Configuração básica da Retrofit.
        //Usar URL base, usar um conversor, e criar o retrofit.
        retrofit = new Retrofit.Builder()
               // .baseUrl("https://viacep.com.br/ws/")
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(DataService.class);

        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //recuperarCEPRetrofit();
                recuperarListaRetrofit();
            }
    });

        botaoExecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //salvarPostagem();
               // atualizarPostagem();
                removerPostagem();
            }
        });
}

            private void removerPostagem(){
                    Call<Void> call = service.removerPostagem(2);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                textoResultado.setText("Status: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
            }

            private void atualizarPostagem(){

                //Configura obj postagem (sem ser JSON)
                Postagem postagem = new Postagem("1234", null, "Corpo postag");
                Call<Postagem> call = service.atualizarPostagem(2, postagem);
                call.enqueue(new Callback<Postagem>() {
                    @Override
                    public void onResponse(Call<Postagem> call, Response<Postagem> response) {

                        if (response.isSuccessful()) {
                            Postagem postagemResposta = response.body();
                            textoResultado.setText(
                                    //response code //ele retorna o código de status da requisição (se deu certo ou não)
                                    "Status: " + response.code() +
                                            " id: " + postagemResposta.getId() + //Id não configuramos
                                            " userId: " + postagemResposta.getUserId() + //Id não configuramos
                                            " titulo: " + postagemResposta.getTitle() + //Titulo
                                            " body: " + postagemResposta.getBody() //Titulo
                            );
                        }
                    }
                    @Override
                    public void onFailure(Call<Postagem> call, Throwable t) {

                    }
                });
            }

            private void salvarPostagem(){
                //Configura objeto postagem
                Postagem postagem = new Postagem("1234", "Título postagem!", "Corpo postagem");

                //recupera o serviço e salva postagem
                DataService service = retrofit.create(DataService.class);
                Call<Postagem> call = service.salvarPostagem(postagem);

                call.enqueue(new Callback<Postagem>() {
                    @Override
                    public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                        if (response.isSuccessful()) {
                            Postagem postagemResposta = response.body();
                            textoResultado.setText(
                                    //response code //ele retorna o código de status da requisição (se deu certo ou não)
                                    "Código: " + response.code() +
                                            " id: " + postagemResposta.getId() + //Id não configuramos
                                            " titulo: " + postagemResposta.getTitle() //Titulo
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<Postagem> call, Throwable t) {

                    }
                });

            }

            private void recuperarListaRetrofit(){

                DataService service = retrofit.create(DataService.class);
                Call<List<Foto>> call = service.recuperarFotos();

                call.enqueue(new Callback<List<Foto>>() {
                    @Override
                    public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                        if (response.isSuccessful())
                            listaFotos = response.body();

                        for(int i=0; i<listaFotos.size(); i++) {
                            Foto foto = listaFotos.get(i);
                            Log.d("resultado", "resultado: " + foto.getId() + " / " + foto.getTitle());

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Foto>> call, Throwable t) {

                    }
                });


                }



            private void recuperarCEPRetrofit(){

              //utilizamos esse cepService para chamar o método, a class faz a configuração pra recuperar os dados.
                CEPService cepService = retrofit.create(CEPService.class);
                //objeto call ira criar a requisição para nós
                Call<CEP> call = cepService.recuperarCEP("01310100");
                //enqueue - sera criado uma tarefa assincrona automaticamente, dentro de uma thread para fazer download para nós
                call.enqueue(new Callback<CEP>() {
                    @Override //nossa resposta, o resultado da requisição
                    public void onResponse(Call<CEP> call, Response<CEP> response) {
                        if (response.isSuccessful()) {
                            CEP cep = response.body(); //já tenho o model configurado
                            textoResultado.setText(cep.getLogradouro() + " / " + cep.getBairro());
                        }

                    }
                    @Override //caso aconteça algum problema
                    public void onFailure(Call<CEP> call, Throwable t) {

                    }
                });
            }
}





