package com.example.afop.ui.main.market.marketDetail

import com.example.afop.data.response.Response

data class MarketDetailResponse (
    val isSuccessGetItem: Boolean? = null,
    val isSuccessModifyItem: Boolean? = null
) : Response()