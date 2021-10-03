package com.james.rxjavamvvm.data.remote.dto.book_detail


import com.google.gson.annotations.SerializedName

data class ListPrice(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("currencyCode")
    val currencyCode: String
)