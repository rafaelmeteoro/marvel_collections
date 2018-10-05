package br.com.rafael.marvelcollections

import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.ThumbnailEntity
import junit.framework.Assert
import org.junit.Test

class CharacterEntityCharacterMapperTests {

    @Test
    fun testMappingCharacterEntityToCharacterReturnsExpectedResult() {

        val thumbnailEntity = ThumbnailEntity(
                path = "PathEntity",
                extension = "ExtensionEntity"
        )

        val characterEntity = CharacterEntity(
                id = 1,
                name = "CharacterName",
                description = "CharacterDescription",
                modified = "CharacterModified",
                thumbnail = thumbnailEntity
        )

        val mapper = CharacterEntityCharacterMapper()
        val character = mapper.mapFrom(characterEntity)

        Assert.assertEquals(characterEntity.id, character.id)
        Assert.assertEquals(characterEntity.name, character.name)
        Assert.assertEquals(characterEntity.description, character.description)
        Assert.assertEquals(characterEntity.modified, character.modified)
        Assert.assertEquals(characterEntity.thumbnail.path, character.thumbnail?.path)
        Assert.assertEquals(characterEntity.thumbnail.extension, character.thumbnail?.extension)
    }
}