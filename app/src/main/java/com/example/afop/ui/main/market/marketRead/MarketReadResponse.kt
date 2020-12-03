package com.example.afop.ui.main.market.marketRead

import com.example.afop.data.response.Response

data class MarketReadResponse (
    val isSuccessGetItem: Boolean? = null,
    val isSuccessModifyItem: Boolean? = null
) : Response()