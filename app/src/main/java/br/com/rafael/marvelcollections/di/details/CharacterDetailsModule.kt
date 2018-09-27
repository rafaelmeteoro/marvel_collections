package br.com.rafael.marvelcollections.di.details

import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.usecases.GetCharacterDetail
import br.com.rafael.marvelcollections.CharacterEntityCharacterMapper
import br.com.rafael.marvelcollections.common.ASyncTransformer
import br.com.rafael.marvelcollections.detail.CharacterDetailVMFactory
import dagger.Module
import dagger.Provides

@Module
class CharacterDetailsModule {

    @Provides
    fun provideGetCharacterDetailsUseCase(charactersRepository: CharactersRepository): GetCharacterDetail {
        return GetCharacterDetail(ASyncTransformer(), charactersRepository)
    }

    @Provides
    fun provideCharacterDetailsVMFactory(getCharacterDetail: GetCharacterDetail,
                                         mapper: CharacterEntityCharacterMapper): CharacterDetailVMFactory {
        return CharacterDetailVMFactory(getCharacterDetail, mapper)
    }
}