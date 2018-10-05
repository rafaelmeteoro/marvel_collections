package br.com.rafael.marvelcollections

import android.arch.lifecycle.Observer
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.common.DomainTestUtils
import br.com.rafael.domain.common.TestTransformer
import br.com.rafael.domain.usecases.GetFavoriteCharacters
import br.com.rafael.marvelcollections.favorites.FavoriteCharactersViewModel
import br.com.rafael.marvelcollections.favorites.FavoriteCharactersViewState
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@Suppress("UNCHECKED_CAST")
@RunWith(AndroidJUnit4::class)
class FavoriteCharactersViewModelTests {

    private val characterEntityCharacterMapper = CharacterEntityCharacterMapper()
    private lateinit var favoriteCharactersViewModel: FavoriteCharactersViewModel
    private lateinit var charactersCache: CharactersCache
    private lateinit var viewObserver: Observer<FavoriteCharactersViewState>
    private lateinit var errorObserver: Observer<Throwable?>

    @Before
    @UiThreadTest
    fun before() {
        charactersCache = Mockito.mock(CharactersCache::class.java)
        val getfavoriteCharacters = GetFavoriteCharacters(TestTransformer(), charactersCache)
        favoriteCharactersViewModel = FavoriteCharactersViewModel(getfavoriteCharacters, characterEntityCharacterMapper)
        viewObserver = Mockito.mock(Observer::class.java) as Observer<FavoriteCharactersViewState>
        errorObserver = Mockito.mock(Observer::class.java) as Observer<Throwable?>
        favoriteCharactersViewModel.viewState.observeForever(viewObserver)
        favoriteCharactersViewModel.errorState.observeForever(errorObserver)
    }

    @Test
    @UiThreadTest
    fun testInitialViewStateShowsLoading() {
        Mockito.verify(viewObserver).onChanged(FavoriteCharactersViewState(isLoading = true, isEmpty = true, characters = null))
        Mockito.verifyZeroInteractions(errorObserver)
    }

    @Test
    @UiThreadTest
    fun testShowingCharactersAsExpectedAndStopsLoading() {
        val characterEntities = DomainTestUtils.generateCharacterEntityList(5)
        Mockito.`when`(charactersCache.getAll()).thenReturn(Observable.just(characterEntities))
        val characters = characterEntities.map { characterEntityCharacterMapper.mapFrom(it) }
        favoriteCharactersViewModel.getFavorites()

        Mockito.verify(viewObserver).onChanged(FavoriteCharactersViewState(isLoading = false, isEmpty = false, characters = characters))
        Mockito.verify(errorObserver).onChanged(null)
    }

    @Test
    @UiThreadTest
    fun testShowingEmptyMessage() {
        Mockito.`when`(charactersCache.getAll()).thenReturn(Observable.just(mutableListOf()))
        favoriteCharactersViewModel.getFavorites()

        Mockito.verify(viewObserver).onChanged(FavoriteCharactersViewState(isLoading = false, isEmpty = true, characters = mutableListOf()))
        Mockito.verify(errorObserver).onChanged(null)
    }

    @Test
    @UiThreadTest
    fun testShowingErrorWhenNeeded() {
        val throwable = Throwable("ERROR!")
        Mockito.`when`(charactersCache.getAll()).thenReturn(Observable.error(throwable))
        favoriteCharactersViewModel.getFavorites()

        Mockito.verify(viewObserver).onChanged(FavoriteCharactersViewState(isLoading = false, isEmpty = false, characters = null))
        Mockito.verify(errorObserver).onChanged(throwable)
    }
}