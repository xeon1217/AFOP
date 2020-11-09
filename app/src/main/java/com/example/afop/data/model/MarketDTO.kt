package com.example.afop.data.model

import java.io.Serializable
import java.util.*

/**
 * 마켓에 사용 될 데이터 모델
 */
data class MarketDTO (
    var marketID: String? = null, //글 아이디
    var sellerUID: String? = null, //판매자
    var buyerUID: String? = null, //구매자
    var title: String? = null, //제목
    var content: String? = null, //글 본문
    var price: String? = null, //가격
    var sold: Boolean? = false, //판매 상태
    var reservation: Boolean? = false, //예약 상태
    var negotiation: Boolean? = false, //흥정 가능
    var timeStamp: Long? = null,
    var lookUpCount: Long? = null, //조회횟수
    var category: String? = null,
    var photos: List<String>? = null //사진, 10장까지
) : Serializable