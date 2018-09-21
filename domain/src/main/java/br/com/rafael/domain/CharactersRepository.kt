package br.com.rafael.domain

import br.com.rafael.domain.entities.ResultEntity
import io.reactivex.Observable

interface CharactersRepository {
    fun getCharacters(): Observable<ResultEntity>
}