package br.com.rafael.domain.entities

data class CharacterEntity(
        var id: Int,
        var name: String,
        var description: String? = null,
        var modified: String,
        var thumbnail: ThumbnailEntity
)