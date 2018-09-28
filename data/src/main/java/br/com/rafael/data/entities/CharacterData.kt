package br.com.rafael.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterData(
        @PrimaryKey
        var id: Int,
        var name: String,
        var description: String? = null,
        var modified: String,
        var path: String,
        var extension: String
)