package br.com.rafael.marvelcollections.di.characters

import br.com.rafael.marvelcollections.CharacterEntityCharacterMapper
import br.com.rafael.marvelcollections.characters.CharactersVMFactory
import dagger.Module
import dagger.Provides

@CharactersScope
@Module
class CharactersModule {

    @Provides
    fun provideCharacterVMFactory(mapper: CharacterEntityCharacterMapper): CharactersVMFactory {
        return CharactersVMFactory(mapper)
    }
}