package com.james.rxjavamvvm.data.remote.dto.book_list


import com.google.gson.annotations.SerializedName

data class Epub(
    @SerializedName("acsTokenLink")
    val acsTokenLink: String,
    @SerializedName("isAvailable")
    val isAvailable: Boolean
)