package com.james.rxjavamvvm.domain.model

import com.james.rxjavamvvm.data.remote.dto.book_detail.ImageLinks

data class BookDetail(
    val id:String,
    val selfLink:String,
    val title:String,
    val author:List<String>,
    val description:String?,
    val pageCount:Int,
    val publisher:String,
    val publishedDate:String?,
    val imageLinks: ImageLinks?
)
