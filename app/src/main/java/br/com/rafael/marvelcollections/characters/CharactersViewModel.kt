package br.com.rafael.marvelcollections.characters

import android.arch.lifecycle.MutableLiveData
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.usecases.GetCharacters
import br.com.rafael.marvelcollections.common.BaseViewModel
import br.com.rafael.marvelcollections.common.SingleLiveEvent
import br.com.rafael.marvelcollections.entities.Character

class CharactersViewModel(private val getCharacters: GetCharacters,
                          private val charEntityCharMapper: Mapper<CharacterEntity, Character>) :
        BaseViewModel() {

    var viewState: MutableLiveData<CharactersViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = CharactersViewState()
    }

    fun getCharacters() {
        addDisposable(getCharacters.observable()
                .flatMap { charEntityCharMapper.observable(it) }
                .subscribe({ characters ->
                    viewState.value?.let {
                        val newState = this.viewState.value?.copy(showLoading = false, characters = characters)
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    viewState.value = viewState.value?.copy(showLoading = false)
                    errorState.value = it
                })
        )
    }
}