package com.james.rxjavamvvm.data.remote.dto.book_detail


import com.google.gson.annotations.SerializedName

data class Epub(
    @SerializedName("isAvailable")
    val isAvailable: Boolean
)