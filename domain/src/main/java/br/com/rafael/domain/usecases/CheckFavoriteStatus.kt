package br.com.rafael.domain.usecases

import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.common.Transformer
import io.reactivex.Observable

class CheckFavoriteStatus(
        transformer: Transformer<Boolean>,
        private val charactersCache: CharactersCache
) : UseCase<Boolean>(transformer) {

    companion object {
        private const val PARAM_CHARACTER_ENTITY = "param:characterEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val characterId = data?.get(PARAM_CHARACTER_ENTITY)
        characterId?.let {
            return charactersCache.get(it as Int).flatMap { optionalCharacterEntity ->
                return@flatMap Observable.just(optionalCharacterEntity.hasValue())
            }
        } ?: return Observable.error({ IllegalArgumentException("CharacterId must be provided.") })
    }

    fun check(characterId: Int): Observable<Boolean> {
        val data = HashMap<String, Int>()
        data[PARAM_CHARACTER_ENTITY] = characterId
        return observable(data)
    }
}