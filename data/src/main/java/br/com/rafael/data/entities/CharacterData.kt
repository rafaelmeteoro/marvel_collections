package br.com.rafael.data.entities

data class CharacterData(
        var id: Int,
        var name: String,
        var description: String? = null,
        var modified: String,
        var thumbnail: ThumbnailData
)