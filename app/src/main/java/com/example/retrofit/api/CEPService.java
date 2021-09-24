package com.example.retrofit.api;

import com.example.retrofit.model.CEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService {

    //retrofit trabalha com CALL que são chamadas do servidor web.
    //utilizamos essa configuração para recuperar os dados
    /*@GET("01310100/json/")
    Call<CEP> recuperarCEP(); sem parametros*/

    //com parametros
    @GET("{cep}/json/")

    //Path cep apontando que o parametro cep corresponde ao valor {cep} definido na Retrofit.
    Call<CEP> recuperarCEP(@Path("cep") String cep);
















    /*@GET("/fotos/")
    Call<CEP> recuperarFotos();*/
}
