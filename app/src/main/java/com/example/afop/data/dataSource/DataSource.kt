package com.example.afop.data.dataSource

import android.content.Context
import android.util.Log
import com.example.afop.data.exception.EmailCheckFailedException
import com.example.afop.data.exception.EmailVerifiedException
import com.example.afop.data.exception.NickNameCheckFailedException
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.UserDTO
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.tasks.await
import org.imperiumlabs.geofirestore.GeoFirestore
import java.util.*
import kotlin.Exception

/**
 * 각종 데이터 입/출력을 책임지는 클래스
 */
class DataSource {
    companion object {
        private lateinit var auth: FirebaseAuth
        private lateinit var db: FirebaseFirestore
        private lateinit var fcm: FirebaseInstanceId
        private lateinit var user: UserDTO

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
    private val dbRefLocation = db.collection("Location")
    private val geoFirestore = GeoFirestore(dbRefUsers)

    private val limit: Long = 10

    /**
     *  유저 관련
     */

    //로그아웃
    fun logout() {
        setAutoLogin(false)
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }

    fun getUID(): String {
        return auth.uid ?: throw Exception()
    }

    suspend fun getUser(_uid: String): UserDTO {
        if (_uid == auth.uid) {
            user = dbRefUsers.document(_uid).get().await().toObject(UserDTO::class.java)
                ?: throw Exception()
        }
        return dbRefUsers.document(_uid).get().await().toObject(UserDTO::class.java)
            ?: throw Exception()
    }

    //사용자 아이디와 패스워드로 로그인 요청을 함
    suspend fun manualLogin(_email: String, _password: String): FirebaseUser {
        auth.signInWithEmailAndPassword(_email, _password).await()
        return auth.currentUser ?: throw FirebaseAuthException("", "")
    }

    //로그인에 성공했을 때, 해당 사용자가 이메일 인증을 마친 상태인지 확인
    fun isEmailVerified(_user: FirebaseUser): Boolean? {
        return if (_user.isEmailVerified) {
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
    suspend fun refreshRemoteFCMToken(_user: FirebaseUser, _token: String) {
        dbRefUsers.document(_user.uid).update(mapOf("FCMToken" to _token)).await()
    }

    //자동 로그인
    fun autoLogin(): FirebaseUser {
        return auth.currentUser ?: throw Exception() // 이 부분에 대해서 보완이 조금 필요할 것 같음
    }

    //자동 로그인 설정
    fun setAutoLogin(_value: Boolean) {
        PreferenceManager.setBoolean("auto_login", _value)
    }

    //이메일 중복 확인
    suspend fun checkEmail(_email: String): Boolean {
        return if (dbRefUsers.whereEqualTo("email", _email).get().await().size() == 0) {
            true
        } else {
            throw EmailCheckFailedException()
        }
    }

    //닉네임 중복 확인
    suspend fun checkNickName(_nickName: String): Boolean {
        return if (dbRefUsers.whereEqualTo("nickName", _nickName).get().await().size() == 0) {
            true
        } else {
            throw NickNameCheckFailedException()
        }
    }

    //회원가입
    suspend fun register(
        _email: String,
        _name: String,
        _password: String,
        _verifyPassword: String,
        _nickName: String
    ) {
        auth.createUserWithEmailAndPassword(_email, _password).await().user?.apply {
            sendEmailVerification() //이메일 인증코드를 발송하도록 함
            dbRefUsers.add(
                UserDTO(
                    email = _email,
                    name = _name,
                    nickName = _nickName
                )
            )
        }
    }

    /**
     * 마켓 관련
     */

    //판매 글 쓰기
    suspend fun marketPutItem(_item: MarketDTO) {
        val timeStamp = Date().time
        _item.sellerUID = auth.uid
        _item.timeStamp = timeStamp
        dbRefMarket.add(_item).await()
    }

    suspend fun marketModifyItem(_item: MarketDTO) {
        Log.d("asdasd", "${_item.marketID}")
        _item.marketID?.let {
            dbRefMarket.document(it)
                .set(_item)
                .await()
        }
    }

    //글 목록 읽기 - 전체, 검색(제목, 내용, 상태), 내가 판매한 것 or 판매 중인 것, 내가 구매 한 것
    suspend fun marketGetList(): QuerySnapshot {
        return dbRefMarket
            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .limit(limit)
            .get()
            .await()
    }

    //글 한 개만 읽기
    fun marketGetItem(_marketID: String) {

    }

    //채팅 관련

    //커뮤니티 관련

    //위치 관련
    suspend fun getLocationRoot(): QuerySnapshot { // 시/도
        return dbRefLocation.get().await()
    }
}

/*
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date
 */

//https://firebase.google.com/docs/auth/android/manage-users#re-authenticate_a_user Firebase auth 보안 규칙
//https://here4you.tistory.com/231 Firestore, https://firebase.google.com/docs/rules/basics?hl=ko#cloud-firestore_5 보안 규칙
//https://firebase.google.com/docs/database/android/structure-data?hl=ko Firestore 데이터 구조화
//https://firebase.google.com/docs/database/unity/retrieve-data?hl=ko Firestore 데이터 검색