package br.com.rafael.marvelcollections.di.favorites

import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.usecases.GetFavoriteCharacters
import br.com.rafael.marvelcollections.CharacterEntityCharacterMapper
import br.com.rafael.marvelcollections.common.ASyncTransformer
import br.com.rafael.marvelcollections.di.DI
import br.com.rafael.marvelcollections.favorites.FavoriteCharactersVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FavoriteModule {

    @Provides
    fun provideGetFavoriteCharacters(@Named(DI.favoritesCache) cache: CharactersCache): GetFavoriteCharacters {
        return GetFavoriteCharacters(ASyncTransformer(), cache)
    }

    @Provides
    fun provideFavoriteCharactersVMFactory(useCase: GetFavoriteCharacters,
                                           mapper: CharacterEntityCharacterMapper): FavoriteCharactersVMFactory {
        return FavoriteCharactersVMFactory(useCase, mapper)
    }
}