package com.fzellner.data.domain.model

data class ComicResponse(
    val data: ComicData?
)

data class ComicData(
    val results: List<ComicResult>?
)

data class ComicResult(
    val id: Long,
    val title: String?,
    val description: String?,
    val thumbnail: ComicThumbnail,
    val prices:List<ComicPrice>?
)

data class ComicThumbnail(val path: String, val extension: String)

data class ComicPrice(val price:Float)