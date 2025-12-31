package com.example.newsapp.home.data

import kotlinx.serialization.Serializable

@Serializable
data class CardNews(
    val id : String,
    val urlImage: String="",
    val cardTitle: String="",
    val caption: String="",
    val date: String="",
    val description: String="",
    val badge: String="",
)
