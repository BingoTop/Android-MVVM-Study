package com.james.rxjavamvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.james.rxjavamvvm.domain.model.BookDetail
import com.james.rxjavamvvm.data.remote.dto.book_detail.toBookDetail
import com.james.rxjavamvvm.common.BaseResponse
import com.james.rxjavamvvm.domain.use_case.book_detail.GetBookDetailUseCase
import com.james.rxjavamvvm.presentation.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getBookDetailUseCase: GetBookDetailUseCase
): BaseViewModel() {
    private val _response = MutableLiveData<BaseResponse<BookDetail>>()
    val response:LiveData<BaseResponse<BookDetail>> get() = _response

    fun getBookById(id:String){
        compositeDisposable.add(
            getBookDetailUseCase.execute(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    _response.postValue(BaseResponse.Loading())
                }
                .subscribe({
                    _response.postValue(BaseResponse.Success(it.toBookDetail()))
                },{
                    _response.postValue(BaseResponse.Error(it))
                })
        )
    }
}