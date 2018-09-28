package br.com.rafael.data.entities

import android.arch.persistence.room.Entity

@Entity(tableName = "character")
data class CharacterData(
        var id: Int,
        var name: String,
        var description: String? = null,
        var modified: String,
        var thumbnail: ThumbnailData
)