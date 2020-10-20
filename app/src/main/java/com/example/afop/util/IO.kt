package com.example.afop.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class IO {
    companion object {
        lateinit var auth: FirebaseAuth
        lateinit var db: FirebaseFirestore
    }
}