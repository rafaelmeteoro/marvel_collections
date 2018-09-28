package br.com.rafael.data

import br.com.rafael.data.api.Api
import br.com.rafael.data.entities.DataData
import br.com.rafael.data.entities.ResultData
import br.com.rafael.data.repositories.RemoteCharactersDataStore
import br.com.rafael.data.utils.DataTestsUtils
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RemoteCharactersDataStoreTests {

    private lateinit var api: Api
    private lateinit var remoteCharactersDataStore: RemoteCharactersDataStore

    @Before
    fun before() {
        api = Mockito.mock(Api::class.java)
        remoteCharactersDataStore = RemoteCharactersDataStore(api)
    }

    @Test
    fun testWhenRequestingCharactersFromRemoteReturnExpectedResult() {
        val size = 5

        Mockito.`when`(api.getCharacters())
                .thenReturn(Observable.just(generateResultData(size)))

        remoteCharactersDataStore.getCharacters().test()
                .assertValue { list -> list.size == size && list[0].name == "CharacterData" }
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