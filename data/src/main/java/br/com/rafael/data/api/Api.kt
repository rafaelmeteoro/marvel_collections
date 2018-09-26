package br.com.rafael.data.api

import br.com.rafael.data.entities.ResultData
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {

    @GET("characters")
    fun getCharacters(): Observable<ResultData>
}