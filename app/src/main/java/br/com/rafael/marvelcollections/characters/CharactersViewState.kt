package br.com.rafael.marvelcollections.characters

import br.com.rafael.marvelcollections.entities.Character

data class CharactersViewState(
        var showLoading: Boolean = true,
        var characters: List<Character>? = null
)