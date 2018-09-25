package br.com.rafael.marvelcollections.entities

data class Character(
        var id: Int = 0,
        var name: String? = null,
        var description: String? = null,
        var modified: String? = null,
        var thumbnail: Thumbnail? = null
)