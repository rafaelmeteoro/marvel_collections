package br.com.rafael.data.entities

data class ResultData(
        var code: Int = 0,
        var status: String,
        var copyright: String,
        var attributionText: String,
        var attributionHTML: String,
        var etag: String,
        var data: DataData? = null
)