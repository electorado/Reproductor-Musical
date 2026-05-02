package com.example.reproductormusical

data class Song(
    val titulo: String,
    val imagen: Int,
    val rawId: Int? = null,
    val uri: String? = null
)