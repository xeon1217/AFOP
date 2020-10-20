package com.example.afop.data.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class User(
        var uid: String? = null, //서버에서 제공되는 유저의 Key
        var fcmToken: String? = null, //서버에서 제공되는 유저의 FCM Token
        var userId: String? = null, //유저의 이메일 주소
        var name: String? = null, //유저의 이름
        var nickName: String? = null //유저의 닉네임
)