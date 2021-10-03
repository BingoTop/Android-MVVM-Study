package com.james.rxjavamvvm.domain.use_case.book_list

import com.james.rxjavamvvm.data.remote.dto.book_list.BookListModelDto
import com.james.rxjavamvvm.domain.repository.MainRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val repository: MainRepository
) {

    fun execute(q:String): Observable<BookListModelDto> {
        return repository.getBookList(q)
    }
}