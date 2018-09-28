package br.com.rafael.data

import br.com.rafael.data.repositories.MemoryCharactersCache
import br.com.rafael.domain.common.DomainTestUtils
import org.junit.Before
import org.junit.Test

class MemoryCharactersCacheTests {

    private lateinit var memoryCharactersCache: MemoryCharactersCache

    @Before
    fun before() {
        memoryCharactersCache = MemoryCharactersCache()
    }

    @Test
    fun testWhenSavingCharactersTheyCanAllBeRetrieved() {
        val size = 5
        memoryCharactersCache.saveAll(DomainTestUtils.generateCharacterEntityList(size))
        memoryCharactersCache.getAll().test()
                .assertValue { list -> list.size == 5 }
                .assertComplete()
    }

    @Test
    fun testSavedCharacterCanBeRetrievedUsingId() {
        val testId = 3
        memoryCharactersCache.save(DomainTestUtils.getTestCharacterEntity(testId))
        memoryCharactersCache.get(testId).test()
                .assertValue { optional ->
                    optional.hasValue() &&
                            optional.value?.name == "Character3" && optional.value?.id == testId
                }
                .assertComplete()
    }

    @Test
    fun testAfterClearingTheRepositoryThereAreNoCharacters() {
        val size = 5
        memoryCharactersCache.saveAll(DomainTestUtils.generateCharacterEntityList(size))
        memoryCharactersCache.getAll().test()
                .assertValue { list -> list.size == size }

        memoryCharactersCache.clear()
        memoryCharactersCache.getAll().test()
                .assertValue { list -> list.isEmpty() }
                .assertComplete()
    }

    @Test
    fun testRemovingCharacterFromCache() {
        val testId = 3
        val characterEntity = DomainTestUtils.getTestCharacterEntity(testId)
        memoryCharactersCache.save(characterEntity)
        memoryCharactersCache.get(testId).test()
                .assertValue { optional -> optional.hasValue() }

        memoryCharactersCache.remove(characterEntity)
        memoryCharactersCache.get(testId).test()
                .assertValue { optional -> !optional.hasValue() }
    }

    @Test
    fun testIsEmptyReturnsExpectedResult() {
        memoryCharactersCache.isEmpty().test().assertValue { value -> value }
        memoryCharactersCache.saveAll(DomainTestUtils.generateCharacterEntityList(5))
        memoryCharactersCache.isEmpty().test().assertValue { value -> !value }
    }
}