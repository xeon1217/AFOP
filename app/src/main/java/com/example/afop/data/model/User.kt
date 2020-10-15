package com.example.afop.data.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class User {
    companion object {
        lateinit var auth: FirebaseAuth
        //lateinit var databaseReference: FirebaseDatabase
    }
}