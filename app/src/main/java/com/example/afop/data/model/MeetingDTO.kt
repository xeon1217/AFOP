package com.example.afop.data.model

data class MeetingDTO (
    val id: String, //모임 id -> Key
    val title: String, //모임 이름
    val introduce: String, //모임 설명
    val member: List<String>, //멤버 리스트
    val board: List<BoardDTO>, //게시판
    val category: String,
) {
    enum class Type(val type: String) {

    }
}