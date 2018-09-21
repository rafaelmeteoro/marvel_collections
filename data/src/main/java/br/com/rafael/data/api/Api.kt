package br.com.rafael.data.api

import br.com.rafael.data.entities.ResultData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("characters")
    fun getCharacters(
            @Query("ts") ts: Long,
            @Query("apikey") apikey: String,
            @Query("hash") hash: String
    ): Observable<ResultData>
}