package br.com.rafael.domain.usecases

import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.common.Transformer
import br.com.rafael.domain.entities.CharacterEntity
import io.reactivex.Observable

class RemoveFavoriteCharacter(
        transformer: Transformer<Boolean>,
        private val charactersCache: CharactersCache
) : UseCase<Boolean>(transformer) {

    companion object {
        private const val PARAM_CHARACTER_ENTITY = "param:characterEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val characterEntity = data?.get(PARAM_CHARACTER_ENTITY)

        characterEntity?.let {
            return Observable.fromCallable {
                val entity = it as CharacterEntity
                charactersCache.remove(entity)
                return@fromCallable false
            }
        }
                ?: return Observable.error({ IllegalArgumentException("CharacterEntity must be provided.") })
    }

    fun remove(characterEntity: CharacterEntity): Observable<Boolean> {
        val data = HashMap<String, CharacterEntity>()
        data[PARAM_CHARACTER_ENTITY] = characterEntity
        return observable(data)
    }
}