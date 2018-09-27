package br.com.rafael.marvelcollections.di

import br.com.rafael.marvelcollections.di.characters.CharactersModule
import br.com.rafael.marvelcollections.di.characters.CharactersSubComponent
import br.com.rafael.marvelcollections.di.details.CharacterDetailsModule
import br.com.rafael.marvelcollections.di.details.CharacterDetailsSubComponent
import br.com.rafael.marvelcollections.di.modules.AppModule
import br.com.rafael.marvelcollections.di.modules.DataModule
import br.com.rafael.marvelcollections.di.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (NetworkModule::class),
    (DataModule::class)
])
interface MainComponent {
    fun plus(charactersModule: CharactersModule): CharactersSubComponent
    fun plus(characterDetailsModule: CharacterDetailsModule): CharacterDetailsSubComponent
}