package com.james.rxjavamvvm.data.remote.dto.book_detail


import com.google.gson.annotations.SerializedName
import com.james.rxjavamvvm.domain.model.BookDetail

data class BookDetailDto(
    @SerializedName("accessInfo")
    val accessInfo: AccessInfo,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("saleInfo")
    val saleInfo: SaleInfo,
    @SerializedName("selfLink")
    val selfLink: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo
)

fun BookDetailDto.toBookDetail(): BookDetail {
    return BookDetail(
        id = id,
        selfLink = selfLink,
        title=volumeInfo.title,
        author=volumeInfo.authors,
        description = volumeInfo.description,
        pageCount=volumeInfo.pageCount,
        publisher = volumeInfo.publisher,
        publishedDate = volumeInfo.publishedDate,
        imageLinks = volumeInfo.imageLinks
    )
}