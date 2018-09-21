package br.com.rafael.domain

import br.com.rafael.domain.entities.CharacterEntity
import io.reactivex.Observable

interface CharactersDataStore {
    fun getCharacters(
            ts: Long,
            apikey: String,
            hash: String
    ): Observable<List<CharacterEntity>>
}