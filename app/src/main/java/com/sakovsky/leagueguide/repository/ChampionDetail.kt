package com.sakovsky.leagueguide.repository
data class ChampionDetail(
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val info: ChampionInfo,
    val blurb: String,
    val partype: String,
    val image: ChampionImage,
    val tags: List<String>,
    val stats: ChampionStats,
    val skins: List<ChampionSkin>,
    val lore: String,
    val allytips: List<String>,
    val enemytips: List<String>,
    val spells: List<ChampionSpell>,
    val passive: ChampionPassive,
    val recommended: List<Any>
)