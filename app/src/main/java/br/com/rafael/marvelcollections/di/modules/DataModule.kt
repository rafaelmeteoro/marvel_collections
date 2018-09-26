package br.com.rafael.marvelcollections.di.modules

import br.com.rafael.data.api.Api
import br.com.rafael.data.repositories.CachedCharactersDataStore
import br.com.rafael.data.repositories.CharactersRepositoryImpl
import br.com.rafael.data.repositories.MemoryCharactersCache
import br.com.rafael.data.repositories.RemoteCharactersDataStore
import br.com.rafael.domain.CharactersCache
import br.com.rafael.domain.CharactersRepository
import br.com.rafael.marvelcollections.di.DI
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
@Singleton
class DataModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(api: Api,
                                   @Named(DI.inMemoryCache) cache: CharactersCache): CharactersRepository {
        val cachedCharactersDataStore = CachedCharactersDataStore(cache)
        val remoteCharactersDataStore = RemoteCharactersDataStore(api)
        return CharactersRepositoryImpl(cachedCharactersDataStore, remoteCharactersDataStore)
    }

    @Singleton
    @Provides
    @Named(DI.inMemoryCache)
    fun provideInMemoryCharactersCache(): CharactersCache {
        return MemoryCharactersCache()
    }
}