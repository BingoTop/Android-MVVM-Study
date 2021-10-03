package com.james.rxjavamvvm.data.remote.dto.book_list


import com.google.gson.annotations.SerializedName

data class SearchInfo(
    @SerializedName("textSnippet")
    val textSnippet: String
)