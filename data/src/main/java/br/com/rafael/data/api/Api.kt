package br.com.rafael.data.api

import br.com.rafael.data.entities.ResultData
import io.reactivex.Observable

interface Api {
    fun getCharacters(): Observable<ResultData>
}