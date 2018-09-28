package br.com.rafael.data.db

import br.com.rafael.data.entities.CharacterData
import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.Optional
import io.reactivex.Observable

class RoomFavoritesCharacterCache(
        database: CharactersDataBase,
        private val entityToDataMapper: Mapper<CharacterEntity, CharacterData>,
        private val dataToEntityMapper: Mapper<CharacterData, CharacterEntity>
) : CharactersCache {

    private val dao: CharacterDao = database.getCharacterDao()

    override fun isEmpty(): Observable<Boolean> {
        return Observable.fromCallable { dao.getCharacters().isEmpty() }
    }

    override fun clear() {
        dao.clear()
    }

    override fun remove(characherEntity: CharacterEntity) {
        dao.removeCharacter(entityToDataMapper.mapFrom(characherEntity))
    }

    override fun save(characherEntity: CharacterEntity) {
        dao.saveCharacter(entityToDataMapper.mapFrom(characherEntity))
    }

    override fun saveAll(characterEntities: List<CharacterEntity>) {
        dao.saveAllCharacters(characterEntities.map { entityToDataMapper.mapFrom(it) })
    }

    override fun get(characterId: Int): Observable<Optional<CharacterEntity>> {
        return Observable.fromCallable {
            val characterData = dao.get(characterId)
            characterData?.let {
                Optional.of(dataToEntityMapper.mapFrom(it))
            } ?: Optional.empty()
        }
    }

    override fun getAll(): Observable<List<CharacterEntity>> {
        return Observable.fromCallable { dao.getCharacters().map { dataToEntityMapper.mapFrom(it) } }
    }
}