package br.com.rafael.marvelcollections.detail

import android.arch.lifecycle.MutableLiveData
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.usecases.GetCharacterDetail
import br.com.rafael.marvelcollections.common.BaseViewModel
import br.com.rafael.marvelcollections.common.SingleLiveEvent
import br.com.rafael.marvelcollections.entities.Character

class CharacterDetailViewModel(private val getCharacterDetail: GetCharacterDetail,
                               private val mapper: Mapper<CharacterEntity, Character>,
                               private val characterId: Int) : BaseViewModel() {

    lateinit var characterEntity: CharacterEntity
    var viewState: MutableLiveData<CharacterDetailViewState> = MutableLiveData()
    var favoriteState: MutableLiveData<Boolean> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()

    init {
        viewState.value = CharacterDetailViewState(isLoading = true)
    }

    fun getCharacterDetails() {
        addDisposable(
                getCharacterDetail.getById(characterId)
                        .map {
                            it.value?.let {
                                characterEntity = it
                                mapper.mapFrom(characterEntity)
                            } ?: run {
                                throw Throwable("Something went wrong :(")
                            }
                        }.subscribe(
                                { onCharacterDetailsReceived(it) },
                                { errorState.value = it }
                        )
        )
    }

    private fun onCharacterDetailsReceived(character: Character) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                name = character.name,
                description = character.description,
                backdropUrl = "${character.thumbnail?.path}.${character.thumbnail?.extension}"
        )

        viewState.value = newViewState
    }
}