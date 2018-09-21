package br.com.rafael.domain.usecases

import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.common.Transformer
import br.com.rafael.domain.entities.ResultEntity
import io.reactivex.Observable

class GetCharacters(
        private val transformer: Transformer<ResultEntity>,
        private val charactersRepository: CharactersRepository
) : UseCase<ResultEntity>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<ResultEntity> {
        return charactersRepository.getCharacters()
    }
}