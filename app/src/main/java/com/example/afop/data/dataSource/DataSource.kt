package com.example.afop.data.dataSource

import android.preference.PreferenceManager
import android.util.Log
import com.example.afop.R
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.login.LoginResult
import com.example.afop.ui.auth.register.RegisterResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import java.util.prefs.Preferences

class DataSource {
    companion object {
        lateinit var auth: FirebaseAuth
        lateinit var db: FirebaseFirestore
        lateinit var fcm: FirebaseInstanceId
    }

    private val dbRefUsers = db.collection("Users")
    private val fcm = FirebaseInstanceId.getInstance()

    //인증 관련
    fun autoLogin(callback: (Result<LoginResult>) -> Unit) {
        fcm.instanceId.addOnCompleteListener(OnCompleteListener { fcmTask -> //FCMToken 갱신
            if(!fcmTask.isSuccessful) {
                callback(Result(state = LoginResult(isLogin = false), error = fcmTask.exception))
                return@OnCompleteListener
            }
            auth.uid?.let { user ->
                dbRefUsers.document(user).update(mapOf("FCMToken" to fcmTask.result?.token)) //DB에 FCMToken 정보를 갱신함
                dbRefUsers.document(user).get().addOnCompleteListener { task -> //유저 정보를 가져오는 역할
                    if (!task.isSuccessful) {
                        callback(Result(state = LoginResult(isLogin = false), error = task.exception))
                        return@addOnCompleteListener
                    }
                    callback(Result(state = LoginResult(isLogin = true)))
                }
            }
        })
    }

    fun login(email: String, password: String, callback: (Result<LoginResult>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task -> //사용자 아이디와 패스워드로 로그인 요청을 함
            if(!task.isSuccessful) {
                callback(Result(state = LoginResult(isLogin = false), error = task.exception))
                return@addOnCompleteListener
            }
            task.addOnSuccessListener { auth -> // 로그인에 성공했을 때, 해당 사용자가 이메일 인증을 마친 상태인지 확인
                if(!auth.user?.isEmailVerified!!) {
                    callback(Result(state = LoginResult(isLogin = false, error = R.string.dialog_message_need_email_verify)))
                    return@addOnSuccessListener
                }
                fcm.instanceId.addOnCompleteListener(OnCompleteListener { fcmTask -> //FCMToken 갱신
                    if(!fcmTask.isSuccessful) {
                        callback(Result(state = LoginResult(isLogin = false), error = fcmTask.exception))
                        return@OnCompleteListener
                    }
                    dbRefUsers.document(auth.user!!.uid).update(mapOf("FCMToken" to fcmTask.result?.token)) //DB에 FCMToken 정보를 갱신함
                    dbRefUsers.document(auth.user!!.uid).get().addOnCompleteListener { task -> //유저 정보를 가져오는 역할
                        if (!task.isSuccessful) {
                            callback(Result(state = LoginResult(isLogin = false), error = task.exception))
                            return@addOnCompleteListener
                        }
                        callback(Result(state = LoginResult(isLogin = true)))
                    }
                })
            }
        }
    }

    fun checkEmail(email: String, callback: (Result<RegisterResult>) -> Unit) {
        try {
            dbRefUsers.whereEqualTo("Email", email).get().addOnSuccessListener { documents -> //서버에 저장된 이메일이 있는지 확인 (이메일 중복 확인)
                if (documents.size() == 0) {
                    callback(Result(state = RegisterResult(isCheckEmail = true)))
                } else {
                    callback(Result(state = RegisterResult(isCheckEmail = false)))
                }
            }
        } catch (e: Exception) {
            callback(Result(state = RegisterResult(isCheckEmail = false), error = e))
        }
    }

    fun checkNickName(nickName: String, callback: (Result<RegisterResult>) -> Unit) {
        try {
            dbRefUsers.whereEqualTo("NickName", nickName).get().addOnSuccessListener { documents -> //서버에 저장된 닉네임이 있는지 확인 (닉네임 중복 확인)
                if (documents.size() == 0) {
                    callback(Result(state = RegisterResult(isCheckNickName = true)))
                } else {
                    callback(Result(state = RegisterResult(isCheckNickName = false)))
                }
            }
        } catch (e: Exception) {
            callback(Result(state = RegisterResult(isCheckNickName = false), error = e))
        }
    }

    fun register(email: String, name: String, password: String, verifyPassword: String, nickName: String, callback: (Result<RegisterResult>) -> Unit) {
        dbRefUsers.whereEqualTo("NickName", nickName).get().addOnCompleteListener { task -> //서버에 저장된 닉네임이 있는지 확인 (닉네임 중복 확인)
            if (task.result?.size() != 0) {
                callback(Result(state = RegisterResult(isCheckNickName = false), error = task.exception))
                return@addOnCompleteListener
            }
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task -> //파라미터로 받은 이메일과 패스워드를 이용해 회원가입을 시도함
                if (task.exception != null) {
                    callback(Result(state = RegisterResult(isRegister = false), error = task.exception))
                    return@addOnCompleteListener
                }
                auth.currentUser?.sendEmailVerification() //이메일 인증코드를 발송하도록 함
                dbRefUsers.document("${auth.uid}").set( //회원 정보를 Firestore DB에 저장되도록 함
                    hashMapOf(
                        "Email" to email,
                        "Name" to name,
                        "NickName" to nickName
                    )
                )
                callback(Result(state = RegisterResult(isRegister = true)))
            }
        }
    }

    //마켓 관련
    //판매글 쓰기
    fun writeMarketContent() {

    }
    //판매글 읽어오기
    //내가 올린 글 읽어오기

    //채팅 관련

    //커뮤니티 관련
}
