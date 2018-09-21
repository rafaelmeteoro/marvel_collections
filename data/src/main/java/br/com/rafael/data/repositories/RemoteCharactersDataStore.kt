package br.com.rafael.data.repositories

import br.com.rafael.data.api.Api
import br.com.rafael.data.mappers.CharactersDataEntityMapper
import br.com.rafael.domain.CharactersDataStore
import br.com.rafael.domain.entities.ResultEntity
import io.reactivex.Observable

class RemoteCharactersDataStore(private val api: Api) : CharactersDataStore {

    private val characterDataMapper = CharactersDataEntityMapper()

    override fun getCharacters(ts: Long, apikey: String, hash: String): Observable<ResultEntity> {
        return api.getCharacters(ts, apikey, hash).map { result ->
            characterDataMapper.mapFrom(result)
        }
    }
}