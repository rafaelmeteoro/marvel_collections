package br.com.rafael.marvelcollections.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.usecases.GetCharacterDetail
import br.com.rafael.marvelcollections.entities.Character

class CharacterDetailVMFactory(
        private val getCharacterDetail: GetCharacterDetail,
        private val mapper: Mapper<CharacterEntity, Character>
) : ViewModelProvider.Factory {

    var characterId: Int = -1

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(
                getCharacterDetail,
                mapper,
                characterId) as T
    }
}