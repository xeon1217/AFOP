package com.example.afop.data.model

/**
 * 유저 데이터를 사용하기 위한 데이터 모델
 */
data class UserDTO(
    val uid: String, //서버에서 제공되는 유저의 Key
    var fcmToken: String? = null, //서버에서 제공되는 유저의 FCM Token
    val token: String, //JWT 토큰
    val email: String? = null, //유저의 이메일 주소
    val name: String? = null, //유저의 이름
    val nickname: String? = null, //유저의 닉네임
    val profileImage: String? = null //프로필 이미지
)