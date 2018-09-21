package br.com.rafael.data.repositories

import br.com.rafael.data.api.Api
import br.com.rafael.data.mappers.CharactersDataEntityMapper
import br.com.rafael.domain.CharactersDataStore
import br.com.rafael.domain.entities.CharacterEntity
import io.reactivex.Observable

class RemoteCharactersDataStore(private val api: Api) : CharactersDataStore {

    private val characterDataMapper = CharactersDataEntityMapper()

    override fun getCharacters(ts: Long, apikey: String, hash: String): Observable<List<CharacterEntity>> {
        return api.getCharacters(ts, apikey, hash).map { results ->
            results.data?.results?.map { characterDataMapper.mapFrom(it) }
        }
    }
}