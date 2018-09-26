package br.com.rafael.marvelcollections.characters

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.usecases.GetCharacters
import br.com.rafael.marvelcollections.entities.Character

class CharactersVMFactory(private val useCase: GetCharacters,
                          private val mapper: Mapper<CharacterEntity, Character>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharactersViewModel(useCase, mapper) as T
    }
}