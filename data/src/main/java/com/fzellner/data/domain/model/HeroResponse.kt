package com.fzellner.data.domain.model

data class HeroResponse(
    val data: HeroData
)

data class HeroData(
    val results: List<HeroResult>
)

data class HeroResult(val id: Long, val name: String?, val description: String?, val thumbnail: HeroThumbnail)


data class HeroThumbnail(val path: String, val extension:String)