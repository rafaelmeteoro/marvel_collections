package br.com.rafael.data.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterData(
        @PrimaryKey
        var id: Int,
        var name: String,
        var description: String? = null,
        var modified: String,
        @Embedded
        var thumbnail: ThumbnailData
)