package com.example.afop.data.model

data class MeetingDTO (
    val id: String,
    val title: String,
    val introduce: String,
    val member: List<String>, //멤버 리스트
    val board: List<BoardDTO>, //게시판
    val images: List<BoardDTO>, //사진첩
    val category: String,
)