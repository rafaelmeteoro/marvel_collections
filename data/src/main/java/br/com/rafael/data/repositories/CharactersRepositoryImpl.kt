package br.com.rafael.data.repositories

import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.entities.CharacterEntity
import io.reactivex.Observable

class CharactersRepositoryImpl(
        private val cachedDataStore: CachedCharactersDataStore,
        private val remoteDataStore: RemoteCharactersDataStore
) : CharactersRepository {

    override fun getCharacters(ts: Long, apikey: String, hash: String): Observable<List<CharacterEntity>> {
        return cachedDataStore.isEmpty().flatMap { empty ->
            if (!empty) {
                return@flatMap cachedDataStore.getCharacters(ts, apikey, hash)
            } else {
                return@flatMap remoteDataStore.getCharacters(ts, apikey, hash)
                        .doOnNext { characters ->
                            cachedDataStore.saveAll(characters)
                        }
            }
        }
    }
}