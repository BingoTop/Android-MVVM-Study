package com.james.rxjavamvvm.data

import com.james.rxjavamvvm.data.remote.dto.book_list.BookListModelDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GetBookListApi {

    @GET("volumes")
    fun getBookList(@Query("q")query:String):Observable<BookListModelDto>
}