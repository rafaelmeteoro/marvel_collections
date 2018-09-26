package br.com.rafael.data.repositories

import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.CharactersDataStore
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.Optional
import io.reactivex.Observable

class CachedCharactersDataStore(private val charactersCache: CharactersCache) : CharactersDataStore {

    override fun getCharacters(): Observable<List<CharacterEntity>> {
        return charactersCache.getAll()
    }

    override fun getCharacterDetail(characterId: Int): Observable<Optional<CharacterEntity>> {
        return charactersCache.get(characterId)
    }

    fun isEmpty(): Observable<Boolean> {
        return charactersCache.isEmpty()
    }

    fun saveAll(charactersEntities: List<CharacterEntity>) {
        charactersCache.saveAll(charactersEntities)
    }
}