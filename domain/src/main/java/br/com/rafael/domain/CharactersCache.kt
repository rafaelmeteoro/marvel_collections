package br.com.rafael.domain

import br.com.rafael.domain.entities.CharacterEntity
import io.reactivex.Observable
import jdk.nashorn.internal.runtime.options.Option

interface CharactersCache {
    fun clear()
    fun save(characherEntity: CharacterEntity)
    fun remove(characherEntity: CharacterEntity)
    fun saveAll(characterEntities: List<CharacterEntity>)
    fun get(characterId: Int): Observable<Option<CharacterEntity>>
    fun getAll(): Observable<List<CharacterEntity>>
    fun isEmpty(): Observable<Boolean>
}