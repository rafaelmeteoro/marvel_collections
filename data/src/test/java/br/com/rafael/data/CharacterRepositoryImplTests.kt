package br.com.rafael.data

import br.com.rafael.data.api.Api
import br.com.rafael.data.entities.DataData
import br.com.rafael.data.entities.ResultData
import br.com.rafael.data.repositories.CachedCharactersDataStore
import br.com.rafael.data.repositories.CharactersRepositoryImpl
import br.com.rafael.data.repositories.RemoteCharactersDataStore
import br.com.rafael.data.utils.DataTestsUtils
import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.common.DomainTestUtils
import br.com.rafael.domain.common.TestCharactersCache
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CharacterRepositoryImplTests {

    private lateinit var api: Api
    private lateinit var characterCache: TestCharactersCache
    private lateinit var charactersRepository: CharactersRepository

    @Before
    fun before() {
        api = Mockito.mock(Api::class.java)
        characterCache = TestCharactersCache()
        val cachedCharactersDataStore = CachedCharactersDataStore(characterCache)
        val remoteCharactersDataStore = RemoteCharactersDataStore(api)
        charactersRepository = CharactersRepositoryImpl(cachedCharactersDataStore, remoteCharactersDataStore)
    }

    @Test
    fun testWhenCacheIsNotEmptyGetCharactersReturnsCachedCharacters() {
        val size = 5
        characterCache.saveAll(DomainTestUtils.generateCharacterEntityList(size))
        charactersRepository.getCharacters().test()
                .assertValue { result -> result.size == size }
                .assertComplete()

        Mockito.verifyZeroInteractions(api)
    }

    @Test
    fun testWhenCacheIsEmptyGetCharactersReturnsCharactersFromApi() {
        val size = 5
        val characterListResult = generateResultData(size)
        Mockito.`when`(api.getCharacters())
                .thenReturn(Observable.just(characterListResult))
        charactersRepository.getCharacters().test()
                .assertValue { list -> list.size == size }
                .assertComplete()
    }

    private fun generateResultData(size: Int): ResultData {
        return ResultData(
                code = 0,
                status = "",
                copyright = "",
                attributionHTML = "",
                attributionText = "",
                etag = "",
                data = DataData(
                        offset = 0,
                        limit = 0,
                        total = 0,
                        count = 0,
                        results = DataTestsUtils.generateCharacterDataList(size)
                )
        )
    }
}