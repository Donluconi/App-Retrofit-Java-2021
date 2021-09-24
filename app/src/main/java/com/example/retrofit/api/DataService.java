package com.example.retrofit.api;

import com.example.retrofit.model.Foto;
import com.example.retrofit.model.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DataService {

    @GET("/photos")
    Call<List<Foto>> recuperarFotos();

    @GET("/posts")
    Call<List<Postagem>> recuperarPostagens();

    @POST("/posts") //@Body é convertido para o formato JSON (encapsulado)
    Call<Postagem> salvarPostagem(@Body Postagem postagem);

    //Ela simula como esses dados são enviados a partir de um formulário
    //userId=1234&title=Titulo postagem&body=Corpo postagem
   /* @FormUrlEncoded
    @POST("/posts") //a rota
    Call<Postagem> salvarPostagem(@Field("userId") String userId,
                                  @Field("title") String title,
                                  @Field("body") String body);*/
    //Dessa maneira os dados não serão convertidos no tipo Json e a API conseguirá ler normalmente.
    //outros tipos de comunicação, depende do client e API, leia documentação.

    //PUT - todos os campos serão atualizados
    @PUT("/posts/{id}") //Path referenciamento parametros
    Call<Postagem> atualizarPostagem(@Path("id") int id, @Body Postagem postagem);

    //PATCH só atualiza os campos que quero enviar.
    @PATCH("/posts/{id}") //Path referenciamento parametros
    Call<Postagem> atualizarPostagemPatch(@Path("id") int id, @Body Postagem postagem);

    //o delete não tem retorno (void)
    @DELETE("/posts/{id}")
    Call<Void> removerPostagem(@Path("id") int id);



}
