package br.com.rafael.domain.entities

data class ResultEntity(
        var code: Int,
        var status: String,
        var copyright: String,
        var attributionText: String,
        var attributionHTML: String,
        var etag: String,
        var data: DataEntity? = null
)