package br.com.rafael.domain.usecases

import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.common.Transformer
import br.com.rafael.domain.entities.CharacterEntity
import io.reactivex.Observable

class GetFavoriteCharacters(
        transformer: Transformer<List<CharacterEntity>>,
        private val cache: CharactersCache
) : UseCase<List<CharacterEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<CharacterEntity>> {
        return cache.getAll()
    }
}