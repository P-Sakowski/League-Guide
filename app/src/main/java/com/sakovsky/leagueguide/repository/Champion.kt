package com.sakovsky.leagueguide.repository
data class Champion(
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val info: ChampionInfo,
    val blurb: String,
    val partype: String,
    val image: ChampionImage,
    val tags: List<String>,
    val stats: ChampionStats
)