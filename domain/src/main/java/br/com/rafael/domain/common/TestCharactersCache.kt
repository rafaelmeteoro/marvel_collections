package br.com.rafael.domain.common

import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.Optional
import io.reactivex.Observable

class TestCharactersCache : CharactersCache {

    private val characters: HashMap<Int, CharacterEntity> = HashMap()

    override fun isEmpty(): Observable<Boolean> {
        return Observable.fromCallable { characters.isEmpty() }
    }

    override fun clear() {
        characters.clear()
    }

    override fun remove(characherEntity: CharacterEntity) {
        characters.remove(characherEntity.id)
    }

    override fun save(characherEntity: CharacterEntity) {
        characters[characherEntity.id] = characherEntity
    }

    override fun saveAll(characterEntities: List<CharacterEntity>) {
        characterEntities.forEach { characterEntity -> this.characters[characterEntity.id] = characterEntity }
    }

    override fun get(characterId: Int): Observable<Optional<CharacterEntity>> {
        return Observable.just(Optional.of(characters[characterId]))
    }

    override fun getAll(): Observable<List<CharacterEntity>> {
        return Observable.just(characters.values.toList())
    }
}