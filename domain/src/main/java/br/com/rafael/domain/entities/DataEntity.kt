package br.com.rafael.domain.entities

data class DataEntity(
        var offset: Int = 0,
        var limit: Int = 0,
        var total: Int = 0,
        var count: Int = 0,
        var results: List<CharacterEntity>? = null
)