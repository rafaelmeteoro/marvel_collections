package br.com.rafael.data

import br.com.rafael.data.repositories.CachedCharactersDataStore
import br.com.rafael.data.repositories.MemoryCharactersCache
import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.CharactersDataStore
import br.com.rafael.domain.common.DomainTestUtils
import org.junit.After
import org.junit.Before
import org.junit.Test

class CachedCharactersDataStoreTests {

    private lateinit var charactersCache: CharactersCache
    private lateinit var cachedCharactersDataStore: CharactersDataStore

    @Before
    fun before() {
        charactersCache = MemoryCharactersCache()
        cachedCharactersDataStore = CachedCharactersDataStore(charactersCache)
    }

    @After
    fun after() {
        charactersCache.clear()
    }

    @Test
    fun testWhenSavingCharactersInCacheTheyCanBeRetrieved() {
        val size = 5
        charactersCache.saveAll(DomainTestUtils.generateCharacterEntityList(size))
        cachedCharactersDataStore.getCharacters().test()
                .assertValue { list -> list.size == size }
                .assertComplete()
    }

    @Test
    fun testSavedCharacterCanBeRetrievedUsingId() {
        val id = 1
        charactersCache.save(DomainTestUtils.getTestCharacterEntity(id))
        cachedCharactersDataStore.getCharacterDetail(id).test()
                .assertValue { optional -> optional.hasValue() && optional.value?.id == id }
                .assertComplete()
    }

    @Test
    fun testWhenRetrievingIdThatDoesNotExistsReturnEmptyOptional() {
        charactersCache.saveAll(DomainTestUtils.generateCharacterEntityList(5))
        cachedCharactersDataStore.getCharacterDetail(18877).test()
                .assertValue { optional -> !optional.hasValue() }
                .assertComplete()
    }
}