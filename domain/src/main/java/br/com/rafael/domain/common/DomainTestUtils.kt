package br.com.rafael.domain.common

import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.ThumbnailEntity

class DomainTestUtils {

    companion object {
        fun getTestCharacterEntity(id: Int): CharacterEntity {
            return CharacterEntity(
                    id = id,
                    name = "Character$id",
                    description = "Description$id",
                    modified = "Modified$id",
                    thumbnail = ThumbnailEntity(
                            path = "Path$id",
                            extension = "Extension$id"
                    )
            )
        }

        fun generateCharacterEntityList(size: Int): List<CharacterEntity> {
            return (1..size).map { getTestCharacterEntity(it) }
        }
    }
}