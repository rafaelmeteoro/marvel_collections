package br.com.rafael.data.api

import br.com.rafael.data.entities.ResultData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("characters")
    fun getCharacters(): Observable<ResultData>

    @GET("characters/{id}")
    fun getCharacterDetail(@Path("id") characterId: Int): Observable<ResultData>
}