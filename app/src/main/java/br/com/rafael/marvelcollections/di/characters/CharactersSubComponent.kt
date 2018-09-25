package br.com.rafael.marvelcollections.di.characters

import br.com.rafael.marvelcollections.characters.CharactersFragment
import dagger.Subcomponent

@Subcomponent(modules = [CharactersModule::class])
interface CharactersSubComponent {
    fun inject(charactersFragment: CharactersFragment)
}