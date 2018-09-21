package br.com.rafael.data.entities

data class DataData(
        var offset: Int,
        var limit: Int,
        var total: Int,
        var count: Int,
        var results: List<CharacterData>? = null
)