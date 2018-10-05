package br.com.rafael.data.repositories

import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.CharactersDataStore
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.Optional
import io.reactivex.Observable

class CachedCharactersDataStore(private val cache: CharactersCache) : CharactersDataStore {

    override fun getCharacters(): Observable<List<CharacterEntity>> {
        return cache.getAll()
    }

    override fun getCharacterDetail(characterId: Int): Observable<Optional<CharacterEntity>> {
        return cache.get(characterId)
    }

    fun isEmpty(): Observable<Boolean> {
        return cache.isEmpty()
    }

    fun saveAll(charactersEntities: List<CharacterEntity>) {
        cache.saveAll(charactersEntities)
    }
}