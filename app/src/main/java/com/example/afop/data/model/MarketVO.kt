package com.example.afop.data.model

import java.io.Serializable
import java.util.*

/**
 * 마켓에 사용 될 데이터 모델
 */
data class MarketVO (
    var marketID: String? = null, //글 아이디
    var sellerUID: String? = null, //판매자
    var buyerUID: String? = null, //구매자
    var title: String? = null, //제목
    var content: String? = null, //글 본문
    var price: String? = null, //가격
    var sale: Boolean? = null, //판매 상태
    var reservation: Boolean? = null, //예약 상태
    var timeStamp: Long? = null,
    var lookUpCount: Int? = null, //조회횟수
    var image: Array<String>? = null //사진, 10장까지
) : Serializable