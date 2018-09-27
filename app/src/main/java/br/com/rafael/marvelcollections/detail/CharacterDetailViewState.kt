package br.com.rafael.marvelcollections.detail

data class CharacterDetailViewState(
        var isLoading: Boolean = true,
        var name: String? = null,
        var description: String? = null,
        var backdropUrl: String? = null
)