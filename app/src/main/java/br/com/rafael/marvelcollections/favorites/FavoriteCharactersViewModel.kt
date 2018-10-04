package br.com.rafael.marvelcollections.favorites

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.usecases.GetFavoriteCharacters
import br.com.rafael.marvelcollections.common.BaseViewModel
import br.com.rafael.marvelcollections.common.SingleLiveEvent
import br.com.rafael.marvelcollections.entities.Character

class FavoriteCharactersViewModel(private val getFavoriteCharacters: GetFavoriteCharacters,
                                  private val mapper: Mapper<CharacterEntity, Character>) : BaseViewModel() {

    var viewState: MutableLiveData<FavoriteCharactersViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = FavoriteCharactersViewState()
        this.viewState.value = viewState
    }

    @SuppressLint("CheckResult")
    fun getFavorites() {
        getFavoriteCharacters.observable()
                .flatMap { mapper.observable(it) }
                .subscribe({ characters ->
                    val newViewState = viewState.value?.copy(
                            isEmpty = characters.isEmpty(),
                            isLoading = false,
                            characters = characters)
                    viewState.value = newViewState
                    errorState.value = null
                }, {
                    viewState.value = viewState.value?.copy(
                            isLoading = false,
                            isEmpty = false)
                    errorState.value = it
                })
    }
}