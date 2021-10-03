package com.james.rxjavamvvm.domain.model

import com.james.rxjavamvvm.data.remote.dto.book_list.ImageLinks

data class BookListModel(
    val id: String,
    val title: String,
    val publisher: String?,
    val description: String?,
    val imageLinks: ImageLinks?
)
