package br.com.rafael.domain.usecases

import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.common.Transformer
import br.com.rafael.domain.entities.CharacterEntity
import io.reactivex.Observable

class GetCharacters(
        transformer: Transformer<List<CharacterEntity>>,
        private val repository: CharactersRepository
) : UseCase<List<CharacterEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<CharacterEntity>> {
        return repository.getCharacters()
    }
}