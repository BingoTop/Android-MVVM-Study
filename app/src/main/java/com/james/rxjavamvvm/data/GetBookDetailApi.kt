package com.james.rxjavamvvm.data

import com.james.rxjavamvvm.data.remote.dto.book_detail.BookDetailDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GetBookDetailApi {
    @GET("volumes/{id}")
    fun getBookDetail(@Path("id")id:String):Single<BookDetailDto>
}