package br.com.rafael.marvelcollections

import android.arch.lifecycle.Observer
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.common.DomainTestUtils
import br.com.rafael.domain.common.TestTransformer
import br.com.rafael.domain.entities.Optional
import br.com.rafael.domain.usecases.CheckFavoriteStatus
import br.com.rafael.domain.usecases.GetCharacterDetail
import br.com.rafael.domain.usecases.RemoveFavoriteCharacter
import br.com.rafael.domain.usecases.SaveFavoriteCharacter
import br.com.rafael.marvelcollections.detail.CharacterDetailViewModel
import br.com.rafael.marvelcollections.detail.CharacterDetailViewState
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@Suppress("UNCHECKED_CAST")
@RunWith(AndroidJUnit4::class)
class CharacterDetailsViewModelTests {

    private val testCharacterId = 100
    private val characterEntityCharacterMapper = CharacterEntityCharacterMapper()
    private lateinit var characterDetailsViewModel: CharacterDetailViewModel
    private lateinit var charactersRepository: CharactersRepository
    private lateinit var charactersCache: CharactersCache
    private lateinit var viewObserver: Observer<CharacterDetailViewState>
    private lateinit var errorObserver: Observer<Throwable>
    private lateinit var favoriteStateObserver: Observer<Boolean>

    @Before
    @UiThreadTest
    fun before() {
        charactersRepository = Mockito.mock(CharactersRepository::class.java)
        charactersCache = Mockito.mock(CharactersCache::class.java)
        val getCharacterDetail = GetCharacterDetail(TestTransformer(), charactersRepository)
        val saveFavoriteCharacter = SaveFavoriteCharacter(TestTransformer(), charactersCache)
        val removeFavoriteCharacter = RemoveFavoriteCharacter(TestTransformer(), charactersCache)
        val checkFavoriteStatus = CheckFavoriteStatus(TestTransformer(), charactersCache)
        characterDetailsViewModel = CharacterDetailViewModel(
                getCharacterDetail,
                checkFavoriteStatus,
                saveFavoriteCharacter,
                removeFavoriteCharacter,
                characterEntityCharacterMapper,
                testCharacterId)

        viewObserver = Mockito.mock(Observer::class.java) as Observer<CharacterDetailViewState>
        favoriteStateObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        errorObserver = Mockito.mock(Observer::class.java) as Observer<Throwable>
        characterDetailsViewModel.viewState.observeForever(viewObserver)
        characterDetailsViewModel.errorState.observeForever(errorObserver)
        characterDetailsViewModel.favoriteState.observeForever(favoriteStateObserver)
    }

    @Test
    @UiThreadTest
    fun showsCorrectDetailsAndFavoriteState() {
        val characterEntity = DomainTestUtils.getTestCharacterEntity(testCharacterId)
        Mockito.`when`(charactersRepository.getCharacterDetail(testCharacterId))
                .thenReturn(Observable.just(Optional.of(characterEntity)))
        Mockito.`when`(charactersCache.get(testCharacterId))
                .thenReturn(Observable.just(Optional.of(characterEntity)))

        characterDetailsViewModel.getCharacterDetails()

        val character = characterEntityCharacterMapper.mapFrom(characterEntity)
        val expectedDetailsViewState = CharacterDetailViewState(
                isLoading = false,
                name = character.name,
                description = character.description,
                backdropUrl = "${character.thumbnail?.path}.${character.thumbnail?.extension}"
        )

        Mockito.verify(viewObserver).onChanged(expectedDetailsViewState)
        Mockito.verify(favoriteStateObserver).onChanged(true)
        Mockito.verifyZeroInteractions(errorObserver)
    }

    @Test
    @UiThreadTest
    fun showsErrorWhenFailsToGetCharacterFromRepository() {
        val characterEntity = DomainTestUtils.getTestCharacterEntity(testCharacterId)
        val throwable = Throwable("ERROR!")

        Mockito.`when`(charactersRepository.getCharacterDetail(testCharacterId))
                .thenReturn(Observable.error(throwable))
        Mockito.`when`(charactersCache.get(testCharacterId))
                .thenReturn(Observable.just(Optional.of(characterEntity)))

        characterDetailsViewModel.getCharacterDetails()

        Mockito.verify(errorObserver).onChanged(throwable)
        Mockito.verifyZeroInteractions(favoriteStateObserver)
    }

    @Test
    @UiThreadTest
    fun showsErrorWhenFailsTogetFavoriteState() {
        val characterEntity = DomainTestUtils.getTestCharacterEntity(testCharacterId)

        Mockito.`when`(charactersRepository.getCharacterDetail(testCharacterId))
                .thenReturn(Observable.just(Optional.empty()))
        Mockito.`when`(charactersCache.get(testCharacterId))
                .thenReturn(Observable.just(Optional.of(characterEntity)))

        characterDetailsViewModel.getCharacterDetails()

        Mockito.verify(errorObserver).onChanged(Mockito.any(Throwable::class.java))
    }

    @Test
    @UiThreadTest
    fun showsErrorWhenGetCharacterFromRepositoryReturnsEmptyOptional() {
        val characterEntity = DomainTestUtils.getTestCharacterEntity(testCharacterId)
        val throwable = Throwable("ERROR!")

        Mockito.`when`(charactersRepository.getCharacterDetail(testCharacterId))
                .thenReturn(Observable.just(Optional.of(characterEntity)))
        Mockito.`when`(charactersCache.get(testCharacterId))
                .thenReturn(Observable.error(throwable))

        characterDetailsViewModel.getCharacterDetails()

        Mockito.verify(errorObserver).onChanged(throwable)
        Mockito.verifyZeroInteractions(favoriteStateObserver)
    }

    @Test
    @UiThreadTest
    fun favoriteStateChangesAsExpected() {
        val characterEntity = DomainTestUtils.getTestCharacterEntity(testCharacterId)
        Mockito.`when`(charactersRepository.getCharacterDetail(testCharacterId))
                .thenReturn(Observable.just(Optional.of(characterEntity)))
        Mockito.`when`(charactersCache.get(testCharacterId))
                .thenReturn(Observable.just(Optional.of(characterEntity)))

        characterDetailsViewModel.getCharacterDetails()
        Mockito.verify(favoriteStateObserver).onChanged(true)

        characterDetailsViewModel.favoriteButtonClicked()
        Mockito.verify(favoriteStateObserver).onChanged(false)

        characterDetailsViewModel.favoriteButtonClicked()
        Mockito.verify(favoriteStateObserver).onChanged(true)

        Mockito.verifyZeroInteractions(errorObserver)
    }
}