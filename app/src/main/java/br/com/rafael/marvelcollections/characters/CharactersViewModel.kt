package br.com.rafael.marvelcollections.characters

import android.arch.lifecycle.MutableLiveData
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.marvelcollections.common.BaseViewModel
import br.com.rafael.marvelcollections.entities.Character

class CharactersViewModel(private val charEntityCharMapper: Mapper<CharacterEntity, Character>) :
        BaseViewModel() {

    var viewState: MutableLiveData<CharactersViewState> = MutableLiveData()

    init {
        viewState.value = CharactersViewState()
    }
}