package com.example.afop.ui.main.market.marketSell

import com.example.afop.data.response.Response

data class MarketSellResponse (
    val isSuccessPutItem: Boolean? = null,
    val isSuccessGetItem: Boolean? = null,
    val isSuccessModifyItem: Boolean? = null
) : Response()