package com.james.rxjavamvvm.data.repository

import com.james.rxjavamvvm.data.GetBookDetailApi
import com.james.rxjavamvvm.data.GetBookListApi
import com.james.rxjavamvvm.data.remote.dto.book_detail.BookDetailDto
import com.james.rxjavamvvm.data.remote.dto.book_list.BookListModelDto
import com.james.rxjavamvvm.domain.repository.MainRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val getBookListApi: GetBookListApi,
    private val getBookDetail:GetBookDetailApi
): MainRepository {
    override fun getBookList(q: String): Observable<BookListModelDto> {
        return getBookListApi.getBookList(q)
    }

    override fun getBookDetail(id: String): Single<BookDetailDto> {
        return getBookDetail.getBookDetail(id)
    }
}