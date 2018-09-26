package br.com.rafael.data.repositories

import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.Optional
import io.reactivex.Observable

class CharactersRepositoryImpl(
        private val cachedDataStore: CachedCharactersDataStore,
        private val remoteDataStore: RemoteCharactersDataStore
) : CharactersRepository {

    override fun getCharacters(): Observable<List<CharacterEntity>> {
        return cachedDataStore.isEmpty().flatMap { empty ->
            if (!empty) {
                return@flatMap cachedDataStore.getCharacters()
            } else {
                return@flatMap remoteDataStore.getCharacters()
                        .doOnNext { characters ->
                            cachedDataStore.saveAll(characters)
                        }
            }
        }
    }

    override fun getCharacterDetail(characterId: Int): Observable<Optional<CharacterEntity>> {
        return remoteDataStore.getCharacterDetail(characterId)
    }
}