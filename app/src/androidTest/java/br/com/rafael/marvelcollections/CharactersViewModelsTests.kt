package br.com.rafael.marvelcollections

import android.arch.lifecycle.Observer
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.common.DomainTestUtils
import br.com.rafael.domain.common.TestTransformer
import br.com.rafael.domain.usecases.GetCharacters
import br.com.rafael.marvelcollections.characters.CharactersViewModel
import br.com.rafael.marvelcollections.characters.CharactersViewState
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@Suppress("UNCHECKED_CAST")
@RunWith(AndroidJUnit4::class)
class CharactersViewModelsTests {

    private val characterEntityCharacterMapper = CharacterEntityCharacterMapper()
    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var charactersRepository: CharactersRepository
    private lateinit var viewObserver: Observer<CharactersViewState>
    private lateinit var errorObserver: Observer<Throwable?>

    @Before
    @UiThreadTest
    fun before() {
        charactersRepository = Mockito.mock(CharactersRepository::class.java)
        val getCharactersUseCase = GetCharacters(TestTransformer(), charactersRepository)
        charactersViewModel = CharactersViewModel(getCharactersUseCase, characterEntityCharacterMapper)
        viewObserver = Mockito.mock(Observer::class.java) as Observer<CharactersViewState>
        errorObserver = Mockito.mock(Observer::class.java) as Observer<Throwable?>
        charactersViewModel.viewState.observeForever(viewObserver)
        charactersViewModel.errorState.observeForever(errorObserver)
    }

    @Test
    @UiThreadTest
    fun testInitialViewStateShowsLoading() {
        Mockito.verify(viewObserver).onChanged(
                CharactersViewState(
                        showLoading = true,
                        characters = null
                )
        )
        Mockito.verifyZeroInteractions(viewObserver)
    }

    @Test
    @UiThreadTest
    fun testShowingCharactersAsExpectedAndStopsLoading() {
        val characterEntities = DomainTestUtils.generateCharacterEntityList(5)
        Mockito.`when`(charactersRepository.getCharacters())
                .thenReturn(Observable.just(characterEntities))

        charactersViewModel.getCharacters()
        val characters = characterEntities.map { characterEntityCharacterMapper.mapFrom(it) }

        Mockito.verify(viewObserver).onChanged(
                CharactersViewState(showLoading = false, characters = characters)
        )
        Mockito.verify(errorObserver).onChanged(null)
    }

    @Test
    @UiThreadTest
    fun testShowingErrorMessageWhenNeeded() {
        val throwable = Throwable("ERROR!")
        Mockito.`when`(charactersRepository.getCharacters())
                .thenReturn(Observable.error(throwable))

        charactersViewModel.getCharacters()

        Mockito.verify(viewObserver).onChanged(
                CharactersViewState(showLoading = false, characters = null)
        )
        Mockito.verify(errorObserver).onChanged(throwable)
    }
}