package com.sakovsky.leagueguide.repository

data class ChampionSpell(
    val id: String,
    val name: String,
    val description: String,
    val tooltip: String,
    val leveltip: ChampionLevelTip,
    val maxrank: Int,
    val cooldown: List<Double>,
    val cooldownBurn: String,
    val cost: List<Int>,
    val costBurn: String,
    val range: List<Int>,
    val rangeBurn: String,
    val image: ChampionImage,
    val resource: String
)
