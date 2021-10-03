package com.james.rxjavamvvm.data.remote.dto.book_list


import com.google.gson.annotations.SerializedName
import com.james.rxjavamvvm.domain.model.BookListModel

data class BookListModelDto(
    @SerializedName("items")
    val items: List<Item>
)


fun Item.toBookListModel(): BookListModel {
    return BookListModel(
        id = id,
        title = volumeInfo.title,
        publisher = volumeInfo.publisher,
        description = volumeInfo.description,
        imageLinks = volumeInfo.imageLinks
    )
}


