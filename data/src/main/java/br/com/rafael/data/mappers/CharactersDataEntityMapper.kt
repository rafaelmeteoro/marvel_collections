package br.com.rafael.data.mappers

import br.com.rafael.data.entities.CharacterData
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.ThumbnailEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersDataEntityMapper @Inject constructor() : Mapper<CharacterData, CharacterEntity>() {

    override fun mapFrom(from: CharacterData): CharacterEntity {
        return CharacterEntity(
                id = from.id,
                name = from.name,
                description = from.description,
                modified = from.modified,
                thumbnail = ThumbnailEntity(from.path, from.extension)
        )
    }
}