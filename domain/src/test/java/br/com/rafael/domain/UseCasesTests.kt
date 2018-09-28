package br.com.rafael.domain

import br.com.rafael.domain.common.DomainTestUtils
import br.com.rafael.domain.common.TestCharactersCache
import br.com.rafael.domain.common.TestTransformer
import br.com.rafael.domain.entities.Optional
import br.com.rafael.domain.usecases.*
import io.reactivex.Observable
import junit.framework.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class UseCasesTests {

    @Test
    fun getCharacterDetailsById() {
        val testId = 100
        val characterEntity = DomainTestUtils.getTestCharacterEntity(testId)
        val characterRepository = Mockito.mock(CharactersRepository::class.java)
        val getCharactersDetails = GetCharacterDetail(TestTransformer(), characterRepository)

        Mockito.`when`(characterRepository.getCharacterDetail(testId))
                .thenReturn(Observable.just(Optional.of(characterEntity)))

        getCharactersDetails.getById(testId).test()
                .assertValue { returnedCharacterEntity ->
                    returnedCharacterEntity.hasValue() &&
                            returnedCharacterEntity.value?.id == testId
                }
                .assertComplete()
    }

    @Test
    fun getCharacters() {
        val size = 5
        val characterList = DomainTestUtils.generateCharacterEntityList(size)
        val charactersRepository = Mockito.mock(CharactersRepository::class.java)
        val getCharacters = GetCharacters(TestTransformer(), charactersRepository)

        Mockito.`when`(charactersRepository.getCharacters())
                .thenReturn(Observable.just(characterList))

        getCharacters.observable().test()
                .assertValue { results -> results.size == size }
                .assertComplete()
    }

    @Test
    fun getCharactersNoResultsReturnEmpty() {
        val charactersRepository = Mockito.mock(CharactersRepository::class.java)
        val getCharacters = GetCharacters(TestTransformer(), charactersRepository)

        Mockito.`when`(charactersRepository.getCharacters())
                .thenReturn(Observable.just(emptyList()))

        getCharacters.observable().test()
                .assertValue { results -> results.isEmpty() }
                .assertComplete()
    }

    @Test
    fun saveCharacterToFavorites() {
        val charactersCache = TestCharactersCache()
        val saveFavoriteCharacter = SaveFavoriteCharacter(TestTransformer(), charactersCache)
        val characterEntity = DomainTestUtils.getTestCharacterEntity(1)

        saveFavoriteCharacter.save(characterEntity).test()
                .assertValue { result -> result }
                .assertComplete()
        charactersCache.get(characterEntity.id).test()
                .assertValue { optionalCharacterEntity ->
                    optionalCharacterEntity.hasValue()
                            && optionalCharacterEntity.value?.id == characterEntity.id
                }
    }

    @Test
    fun getFavoriteCharacters() {
        val size = 5
        val characterList = DomainTestUtils.generateCharacterEntityList(size)
        val charactersCache = Mockito.mock(CharactersCache::class.java)
        val getFavoriteCharacter = GetFavoriteCharacters(TestTransformer(), charactersCache)

        Mockito.`when`(charactersCache.getAll())
                .thenReturn(Observable.just(characterList))

        getFavoriteCharacter.observable().test()
                .assertValue { results -> results.size == size }
                .assertComplete()
    }

    @Test
    fun removeFavoriteCharacter() {
        val characterCache = TestCharactersCache()
        val saveFavoriteCharacter = SaveFavoriteCharacter(TestTransformer(), characterCache)
        val removeFavoriteCharacter = RemoveFavoriteCharacter(TestTransformer(), characterCache)
        val characterEntity = DomainTestUtils.getTestCharacterEntity(1)

        saveFavoriteCharacter.save(characterEntity)
        Assert.assertNotNull(characterCache.get(characterEntity.id))
        removeFavoriteCharacter.remove(characterEntity).test()
                .assertValue { returnedValue -> !returnedValue }
                .assertComplete()
        characterCache.get(characterEntity.id).test()
                .assertValue { optionalEntity -> !optionalEntity.hasValue() }
    }

    @Test
    fun testCheckFavoriteStatus() {
        val charId99 = 99
        val charId100 = 100
        val characterEntity = DomainTestUtils.getTestCharacterEntity(charId100)
        val charactersCache = Mockito.mock(CharactersCache::class.java)
        val checkFavoriteStatus = CheckFavoriteStatus(TestTransformer(), charactersCache)

        Mockito.`when`(charactersCache.get(charId99))
                .thenReturn(Observable.just(Optional.empty()))
        Mockito.`when`(charactersCache.get(charId100))
                .thenReturn(Observable.just(Optional.of(characterEntity)))

        checkFavoriteStatus.check(charId99).test()
                .assertValue { result -> !result }
                .assertComplete()
        checkFavoriteStatus.check(charId100).test()
                .assertValue { result -> result }
                .assertComplete()
    }
}