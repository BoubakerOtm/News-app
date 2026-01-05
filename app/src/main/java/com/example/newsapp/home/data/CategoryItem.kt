package com.example.newsapp.home.data

data class CategoryItem(
    val id: Int,
    val label: String,
    val value: String,
)

val getItems = listOf(
    CategoryItem(1, "General", "general"),
    CategoryItem(2, "Entertainment", "entertainment"),
    CategoryItem(3, "Technology", "technology"),
    CategoryItem(4, "Health", "health"),
    CategoryItem(5, "Science", "science"),
    CategoryItem(6, "Sports", "sports"),
    CategoryItem(7, "Business", "business"),
)



