package br.com.rafael.marvelcollections.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.usecases.CheckFavoriteStatus
import br.com.rafael.domain.usecases.GetCharacterDetail
import br.com.rafael.domain.usecases.RemoveFavoriteCharacter
import br.com.rafael.domain.usecases.SaveFavoriteCharacter
import br.com.rafael.marvelcollections.entities.Character

class CharacterDetailVMFactory(
        private val getCharacterDetail: GetCharacterDetail,
        private val checkFavoriteStatus: CheckFavoriteStatus,
        private val saveFavoriteCharacter: SaveFavoriteCharacter,
        private val removeFavoriteCharacter: RemoveFavoriteCharacter,
        private val mapper: Mapper<CharacterEntity, Character>
) : ViewModelProvider.Factory {

    var characterId: Int = -1

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(
                getCharacterDetail,
                checkFavoriteStatus,
                saveFavoriteCharacter,
                removeFavoriteCharacter,
                mapper,
                characterId) as T
    }
}