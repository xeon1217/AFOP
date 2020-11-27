package com.example.afop.data.model

import android.net.Uri

/**
 * 마켓에 사용 될 데이터 모델
 */

data class MarketDTO(
    val marketID: Long? = null, //글 아이디
    var sellerUID: String? = null, //판매자
    var buyerUID: String? = null, //구매자
    var title: String? = null, //제목
    var content: String? = null, //글 본문
    var price: String? = null, //가격
    var state: Int = State.SOLD.ordinal, //상태, 판매중, 예약중, 판매완료
    var negotiation: Boolean? = false, //흥정 가능
    var timeStamp: Long? = null,
    var lookUpCount: Long? = null, //조회횟수
    var category: String? = null,
    var images: ArrayList<String> = arrayListOf() //사진, 10장까지
) {
    enum class State(val string: String) {
        SOLD("판매중"),
        RESERVATION("예약중"),
        SOLD_OUT("판매완료")
    }

    enum class Category(val string: String) {

    }
}