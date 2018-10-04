package br.com.rafael.marvelcollections.di.favorites

import br.com.rafael.marvelcollections.favorites.FavoriteCharactersFragment
import dagger.Subcomponent

@FavoritesScope
@Subcomponent(modules = [FavoriteModule::class])
interface FavoriteSubComponent {
    fun inject(favoriteCharactersFragment: FavoriteCharactersFragment)
}