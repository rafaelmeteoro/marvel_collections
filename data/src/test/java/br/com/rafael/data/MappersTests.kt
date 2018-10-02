package br.com.rafael.data

import br.com.rafael.data.mappers.CharacterEntityDataMapper
import br.com.rafael.data.mappers.CharactersDataEntityMapper
import br.com.rafael.data.utils.DataTestsUtils
import br.com.rafael.domain.common.DomainTestUtils
import org.junit.Assert
import org.junit.Test

class MappersTests {

    @Test
    fun testMappingCharacterDataToCharacterEntityReturnsExpectedResult() {
        val characterData = DataTestsUtils.getMockedCharacterData(40)
        val mapper = CharactersDataEntityMapper()
        val characterEntity = mapper.mapFrom(characterData)

        Assert.assertEquals(characterEntity.id, characterData.id)
        Assert.assertEquals(characterEntity.name, characterData.name)
        Assert.assertEquals(characterEntity.description, characterData.description)
        Assert.assertEquals(characterEntity.modified, characterData.modified)
        Assert.assertEquals(characterEntity.thumbnail.path, characterData.thumbnail.path)
        Assert.assertEquals(characterEntity.thumbnail.extension, characterData.thumbnail.extension)
    }

    @Test
    fun testMappingCharacterEntityToCharacterDataReturnsExpectedResult() {
        val characterEntity = DomainTestUtils.getTestCharacterEntity(40)
        val mapper = CharacterEntityDataMapper()
        val characterData = mapper.mapFrom(characterEntity)

        Assert.assertEquals(characterEntity.id, characterData.id)
        Assert.assertEquals(characterEntity.name, characterData.name)
        Assert.assertEquals(characterEntity.description, characterData.description)
        Assert.assertEquals(characterEntity.modified, characterData.modified)
        Assert.assertEquals(characterEntity.thumbnail.path, characterData.thumbnail.path)
        Assert.assertEquals(characterEntity.thumbnail.extension, characterData.thumbnail.extension)
    }
}