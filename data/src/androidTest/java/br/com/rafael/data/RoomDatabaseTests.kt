package br.com.rafael.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import br.com.rafael.data.db.CharacterDao
import br.com.rafael.data.db.CharactersDataBase
import br.com.rafael.data.utils.DataTestsUtils
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTests {

    private lateinit var database: CharactersDataBase
    private lateinit var characterDao: CharacterDao

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                CharactersDataBase::class.java).build()

        characterDao = database.getCharacterDao()
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    fun testSingleInsertion() {
        val characterData = DataTestsUtils.getMockedCharacterData(21)
        characterDao.saveCharacter(characterData)
        val result = characterDao.get(characterData.id)
        Assert.assertNotNull(result)
        Assert.assertEquals(result?.id, characterData.id)
    }

    @Test
    fun testMultipleInsertions() {
        val size = 5
        val characterDataList = DataTestsUtils.generateCharacterDataList(size)
        characterDao.saveAllCharacters(characterDataList)
        val characters = characterDao.getCharacters()
        Assert.assertEquals(characters.size, size)
    }

    @Test
    fun testRemoval() {
        val characterData = DataTestsUtils.getMockedCharacterData(22)
        characterDao.saveCharacter(characterData)
        val characters = characterDao.getCharacters()
        Assert.assertEquals(characters.size, 1)

        characterDao.removeCharacter(characterData)
        val characters2 = characterDao.getCharacters()
        Assert.assertEquals(characters2.size, 0)
    }

    @Test
    fun testAllDataIsSaved() {
        val characterData = DataTestsUtils.getMockedCharacterData(432)
        characterDao.saveCharacter(characterData)
        val characters = characterDao.getCharacters()
        val (id, name, description, modified, thumbnail) = characters[0]
        Assert.assertEquals(id, characterData.id)
        Assert.assertEquals(name, characterData.name)
        Assert.assertEquals(description, characterData.description)
        Assert.assertEquals(modified, characterData.modified)
        Assert.assertEquals(thumbnail.path, characterData.thumbnail.path)
        Assert.assertEquals(thumbnail.extension, characterData.thumbnail.extension)
    }

    @Test
    fun testClearTable() {
        val size = 5
        val characterDataList = DataTestsUtils.generateCharacterDataList(size)
        characterDao.saveAllCharacters(characterDataList)
        val characters = characterDao.getCharacters()
        Assert.assertEquals(characters.size, size)
        characterDao.clear()
        Assert.assertTrue(characterDao.getCharacters().isEmpty())
    }
}