package br.com.rafael.domain.usecases

import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.common.Transformer
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.Optional
import io.reactivex.Observable

class GetCharacterDetail(
        transformer: Transformer<Optional<CharacterEntity>>,
        private val charactersRepository: CharactersRepository
) : UseCase<Optional<CharacterEntity>>(transformer) {

    companion object {
        private const val PARAM_CHARACTER_ENTITY = "param:characterEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Optional<CharacterEntity>> {
        val characterId = data?.get(PARAM_CHARACTER_ENTITY)
        characterId?.let {
            return charactersRepository.getCharacterDetail(it as Int)
        } ?: return Observable.error({ IllegalArgumentException("CharacterId must be provided.") })
    }

    fun getById(characterId: Int): Observable<Optional<CharacterEntity>> {
        val data = HashMap<String, Int>()
        data[PARAM_CHARACTER_ENTITY] = characterId
        return observable(data)
    }
}