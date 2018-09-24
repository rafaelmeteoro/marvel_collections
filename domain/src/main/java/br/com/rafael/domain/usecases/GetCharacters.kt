package br.com.rafael.domain.usecases

import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.common.Transformer
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.ResultEntity
import io.reactivex.Observable

class GetCharacters(
        private val transformer: Transformer<List<CharacterEntity>>,
        private val charactersRepository: CharactersRepository
) : UseCase<List<CharacterEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<CharacterEntity>> {
        return charactersRepository.getCharacters(0L, "", "")
    }
}