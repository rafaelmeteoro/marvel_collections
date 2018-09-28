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

        fun generateCharacterEntityList(): List<CharacterEntity> {
            return (0..4).map { getTestCharacterEntity(it) }
        }
    }
}