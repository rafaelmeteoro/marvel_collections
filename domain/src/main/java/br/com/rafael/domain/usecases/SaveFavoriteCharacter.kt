package br.com.rafael.domain.usecases

import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.common.Transformer
import br.com.rafael.domain.entities.CharacterEntity
import io.reactivex.Observable

class SaveFavoriteCharacter(
        transformer: Transformer<Boolean>,
        private val cache: CharactersCache
) : UseCase<Boolean>(transformer) {

    companion object {
        private const val PARAM_CHARACTER_ENTITY = "param:characterEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val characterEntity = data?.get(PARAM_CHARACTER_ENTITY)

        characterEntity?.let {
            return Observable.fromCallable {
                val entity = it as CharacterEntity
                cache.save(entity)
                return@fromCallable true
            }
        } ?: return Observable.error { IllegalArgumentException("CharacterId must be provided.") }
    }

    fun save(characterEntity: CharacterEntity): Observable<Boolean> {
        val data = HashMap<String, CharacterEntity>()
        data[PARAM_CHARACTER_ENTITY] = characterEntity
        return observable(data)
    }
}