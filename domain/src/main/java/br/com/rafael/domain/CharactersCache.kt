package br.com.rafael.domain

import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.Optional
import io.reactivex.Observable

interface CharactersCache {
    fun isEmpty(): Observable<Boolean>
    fun clear()
    fun remove(characherEntity: CharacterEntity)
    fun save(characherEntity: CharacterEntity)
    fun saveAll(characterEntities: List<CharacterEntity>)
    fun get(characterId: Int): Observable<Optional<CharacterEntity>>
    fun getAll(): Observable<List<CharacterEntity>>
}