package br.com.rafael.marvelcollections.di.details

import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.CharactersRepository
import br.com.rafael.domain.usecases.CheckFavoriteStatus
import br.com.rafael.domain.usecases.GetCharacterDetail
import br.com.rafael.domain.usecases.RemoveFavoriteCharacter
import br.com.rafael.domain.usecases.SaveFavoriteCharacter
import br.com.rafael.marvelcollections.CharacterEntityCharacterMapper
import br.com.rafael.marvelcollections.common.ASyncTransformer
import br.com.rafael.marvelcollections.detail.CharacterDetailVMFactory
import br.com.rafael.marvelcollections.di.DI
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CharacterDetailsModule {

    @Provides
    fun provideGetCharacterDetailsUseCase(charactersRepository: CharactersRepository): GetCharacterDetail {
        return GetCharacterDetail(ASyncTransformer(), charactersRepository)
    }

    @Provides
    fun provideCheckFavoriteStatus(@Named(DI.favoritesCache) charactersCache: CharactersCache): CheckFavoriteStatus {
        return CheckFavoriteStatus(ASyncTransformer(), charactersCache)
    }

    @Provides
    fun provideSaveFavoriteCharacter(@Named(DI.favoritesCache) charactersCache: CharactersCache): SaveFavoriteCharacter {
        return SaveFavoriteCharacter(ASyncTransformer(), charactersCache)
    }

    @Provides
    fun provideRemoveFavoriteCharacter(@Named(DI.favoritesCache) charactersCache: CharactersCache): RemoveFavoriteCharacter {
        return RemoveFavoriteCharacter(ASyncTransformer(), charactersCache)
    }

    @Provides
    fun provideCharacterDetailsVMFactory(getCharacterDetail: GetCharacterDetail,
                                         checkFavoriteStatus: CheckFavoriteStatus,
                                         saveFavoriteCharacter: SaveFavoriteCharacter,
                                         removeFavoriteCharacter: RemoveFavoriteCharacter,
                                         mapper: CharacterEntityCharacterMapper): CharacterDetailVMFactory {
        return CharacterDetailVMFactory(
                getCharacterDetail,
                checkFavoriteStatus,
                saveFavoriteCharacter,
                removeFavoriteCharacter,
                mapper
        )
    }
}