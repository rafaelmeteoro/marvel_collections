package br.com.rafael.domain

import br.com.rafael.domain.entities.CharacterEntity
import io.reactivex.Observable

interface CharactersRepository {
    fun getCharacters(): Observable<List<CharacterEntity>>
}