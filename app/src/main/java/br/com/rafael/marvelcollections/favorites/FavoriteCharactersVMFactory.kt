package br.com.rafael.marvelcollections.favorites

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.usecases.GetFavoriteCharacters
import br.com.rafael.marvelcollections.entities.Character

class FavoriteCharactersVMFactory(private val useCase: GetFavoriteCharacters,
                                  private val mapper: Mapper<CharacterEntity, Character>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteCharactersViewModel(useCase, mapper) as T
    }
}