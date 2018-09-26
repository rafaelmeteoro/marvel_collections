package br.com.rafael.marvelcollections.di.characters

import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.usecases.GetCharacters
import br.com.rafael.marvelcollections.CharacterEntityCharacterMapper
import br.com.rafael.marvelcollections.characters.CharactersVMFactory
import br.com.rafael.marvelcollections.common.ASyncTransformer
import dagger.Module
import dagger.Provides

@CharactersScope
@Module
class CharactersModule {

    @Provides
    fun provideGetCharactersUseCase(charactersRepository: CharactersRepository): GetCharacters {
        return GetCharacters(ASyncTransformer(), charactersRepository)
    }

    @Provides
    fun provideCharacterVMFactory(useCase: GetCharacters,
                                  mapper: CharacterEntityCharacterMapper): CharactersVMFactory {
        return CharactersVMFactory(useCase, mapper)
    }
}