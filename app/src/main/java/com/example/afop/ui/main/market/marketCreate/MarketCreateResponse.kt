package com.example.afop.ui.main.market.marketCreate

import com.example.afop.data.response.Response

data class MarketCreateResponse (
    val isSuccessPutItem: Boolean? = null,
    val isSuccessGetItem: Boolean? = null,
    val isSuccessModifyItem: Boolean? = null
) : Response()