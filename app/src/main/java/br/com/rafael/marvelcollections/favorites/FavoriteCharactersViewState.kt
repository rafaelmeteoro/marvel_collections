package br.com.rafael.marvelcollections.favorites

import br.com.rafael.marvelcollections.entities.Character

data class FavoriteCharactersViewState(
        val isLoading: Boolean = true,
        val isEmpty: Boolean = true,
        val characters: List<Character>? = null
)