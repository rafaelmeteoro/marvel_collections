package br.com.rafael.data.mappers

import br.com.rafael.data.entities.CharacterData
import br.com.rafael.data.entities.ThumbnailData
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterEntityDataMapper @Inject constructor() : Mapper<CharacterEntity, CharacterData>() {

    override fun mapFrom(from: CharacterEntity): CharacterData {
        return CharacterData(
                id = from.id,
                name = from.name,
                description = from.description,
                modified = from.modified,
                thumbnail = ThumbnailData(from.thumbnail.path, from.thumbnail.extension)
        )
    }
}