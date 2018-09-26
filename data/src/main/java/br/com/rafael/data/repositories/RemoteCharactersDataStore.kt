package br.com.rafael.data.repositories

import br.com.rafael.data.api.Api
import br.com.rafael.data.mappers.CharactersDataEntityMapper
import br.com.rafael.domain.CharactersDataStore
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.Optional
import io.reactivex.Observable

class RemoteCharactersDataStore(private val api: Api) : CharactersDataStore {

    private val characterDataMapper = CharactersDataEntityMapper()

    override fun getCharacters(): Observable<List<CharacterEntity>> {
        return api.getCharacters().map { results ->
            results.data?.results?.map { characterDataMapper.mapFrom(it) }
        }
    }

    override fun getCharacterDetail(characterId: Int): Observable<Optional<CharacterEntity>> {
        return api.getCharacterDetail(characterId).flatMap { results ->
            results.data?.let {
                it.results?.first().let { data ->
                    Observable.just(Optional.of(characterDataMapper.mapFrom(data!!)))
                }
            }
        }
    }
}