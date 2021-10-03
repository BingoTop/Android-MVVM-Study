package com.james.rxjavamvvm.domain.repository

import com.james.rxjavamvvm.data.remote.dto.book_detail.BookDetailDto
import com.james.rxjavamvvm.data.remote.dto.book_list.BookListModelDto
import io.reactivex.Observable
import io.reactivex.Single

interface MainRepository {
    fun getBookList(q:String):Observable<BookListModelDto>
    fun getBookDetail(id:String): Single<BookDetailDto>
}