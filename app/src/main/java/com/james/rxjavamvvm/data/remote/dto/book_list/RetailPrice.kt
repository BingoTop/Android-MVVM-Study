package com.james.rxjavamvvm.data.remote.dto.book_list


import com.google.gson.annotations.SerializedName

data class RetailPrice(
    @SerializedName("amountInMicros")
    val amountInMicros: Long,
    @SerializedName("currencyCode")
    val currencyCode: String
)