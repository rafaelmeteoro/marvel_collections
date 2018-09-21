package br.com.rafael.data.entities

data class ResultData(
        var code: Int = 0,
        var status: String? = null,
        var copyright: String? = null,
        var attributionText: String? = null,
        var attributionHTML: String? = null,
        var etag: String? = null,
        var data: DataData? = null
)