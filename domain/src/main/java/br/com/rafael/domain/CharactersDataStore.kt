package br.com.rafael.domain

import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.Optional
import io.reactivex.Observable

interface CharactersDataStore {
    fun getCharacters(): Observable<List<CharacterEntity>>
    fun getCharacterDetail(characterId: Int): Observable<Optional<CharacterEntity>>
}