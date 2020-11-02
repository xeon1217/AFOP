package com.example.afop.data.dataSource

import android.content.Context
import com.example.afop.data.exception.EmailCheckFailedException
import com.example.afop.data.exception.EmailVerifiedException
import com.example.afop.data.exception.NickNameCheckFailedException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.tasks.await
import org.imperiumlabs.geofirestore.GeoFirestore
import kotlin.Exception

/**
 * 각종 데이터 입/출력을 책임지는 클래스
 */
class DataSource {
    companion object {
        private lateinit var auth: FirebaseAuth
        private lateinit var db: FirebaseFirestore
        private lateinit var fcm: FirebaseInstanceId

        fun init(context: Context) {
            auth = FirebaseAuth.getInstance()
            db = FirebaseFirestore.getInstance()
            fcm = FirebaseInstanceId.getInstance()
            PreferenceManager.init(context)
        }

        fun exit() {
            if (!isAutoLogin()) {
                auth.signOut()
            }
        }

        fun isAutoLogin(): Boolean {
            return PreferenceManager.getBoolean("auto_login") && auth.currentUser != null
        }
    }

    private val dbRefUsers = db.collection("Users")
    private val dbRefMarket = db.collection("Market")
    private val geoFirestore = GeoFirestore(dbRefUsers)

    //유저 관련
    fun logout() {
        setAutoLogin(false)
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }

    //사용자 아이디와 패스워드로 로그인 요청을 함
    suspend fun manualLogin(email: String, password: String): FirebaseUser {
        auth.signInWithEmailAndPassword(email, password).await()
        return auth.currentUser ?: throw FirebaseAuthException("", "")
    }

    //로그인에 성공했을 때, 해당 사용자가 이메일 인증을 마친 상태인지 확인
    fun isEmailVerified(user: FirebaseUser): Boolean? {
        return if (user.isEmailVerified) {
            true
        } else {
            throw EmailVerifiedException()
        }
    }

    //FCMToken 갱신
    suspend fun refreshLocalFCMToken(): String {
        return fcm.instanceId.await().token
    }

    //DB에 FCMToken 정보를 갱신함
    suspend fun refreshRemoteFCMToken(user: FirebaseUser, token: String) {
        dbRefUsers.document(user.uid).update(mapOf("FCMToken" to token)).await()
    }

    //유저 정보를 가져오는 역할
    suspend fun getUser(user: FirebaseUser) {
        dbRefUsers.document(user.uid).get().await()
    }

    fun autoLogin(): FirebaseUser {
        return auth.currentUser ?:
        throw Exception() // 이 부분에 대해서 보완이 조금 필요할 것 같음
    }

    fun setAutoLogin(value: Boolean) {
        PreferenceManager.setBoolean("auto_login", value)
    }

    suspend fun checkEmail(email: String): Boolean {
        return if (dbRefUsers.whereEqualTo("Email", email).get().await().size() == 0) {
            true
        } else {
            throw EmailCheckFailedException()
        }
    }

    suspend fun checkNickName(nickName: String): Boolean {
        return if (dbRefUsers.whereEqualTo("NickName", nickName).get().await().size() == 0) {
            true
        } else {
            throw NickNameCheckFailedException()
        }
    }

    suspend fun register(email: String, name: String, password: String, verifyPassword: String, nickName: String) {
        auth.createUserWithEmailAndPassword(email, password).await().user?.apply {
            sendEmailVerification() //이메일 인증코드를 발송하도록 함
            dbRefUsers.document("${auth.uid}").set( //회원 정보를 Firestore DB에 저장되도록 함
                hashMapOf(
                    "Email" to email,
                    "Name" to name,
                    "NickName" to nickName
                )
            )
        }
    }

    /*
    //마켓 관련
    //판매글 올리기
    fun sale(content: MarketVO, callback: (Result<MarketSellResult>) -> Unit) {
        content.sellerUID = auth.uid
        content.lookUpCount = 0
        content.timeStamp = Date().time
        dbRefMarket.document().set(
            content
        )
    }
    //판매글 읽어오기
    //내가 쓴 글 확인하기
    //

    //채팅 관련

    //커뮤니티 관련
    */
}

//https://firebase.google.com/docs/auth/android/manage-users#re-authenticate_a_user Firebase auth 보안 규칙
//https://here4you.tistory.com/231 Firestore, https://firebase.google.com/docs/rules/basics?hl=ko#cloud-firestore_5 보안 규칙
//https://firebase.google.com/docs/database/android/structure-data?hl=ko Firestore 데이터 구조화
//https://firebase.google.com/docs/database/unity/retrieve-data?hl=ko Firestore 데이터 검색