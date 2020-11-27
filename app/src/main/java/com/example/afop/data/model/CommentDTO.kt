package com.example.afop.data.model

data class CommentDTO(
    val uid: String, //쓴사람
    val comment: String, //내용
    val timeStamp: Long //시간
)