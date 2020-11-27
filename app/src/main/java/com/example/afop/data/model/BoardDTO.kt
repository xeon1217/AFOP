package com.example.afop.data.model

data class BoardDTO(
    val id: String,
    val image: List<String>,
    val timeStamp: Long,
    val content: String,
    val comment: List<CommentDTO>
)