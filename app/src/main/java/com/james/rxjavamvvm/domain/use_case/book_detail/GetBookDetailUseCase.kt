package com.james.rxjavamvvm.domain.use_case.book_detail

import com.james.rxjavamvvm.data.remote.dto.book_detail.BookDetailDto
import com.james.rxjavamvvm.data.repository.MainRepositoryImpl
import io.reactivex.Single
import javax.inject.Inject

class GetBookDetailUseCase @Inject constructor(
    private val repositoryImpl: MainRepositoryImpl
) {
    fun execute(id:String): Single<BookDetailDto> = repositoryImpl.getBookDetail(id)

}