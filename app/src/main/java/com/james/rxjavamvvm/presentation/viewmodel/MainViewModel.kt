package com.james.rxjavamvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.james.rxjavamvvm.domain.model.BookListModel
import com.james.rxjavamvvm.data.remote.dto.book_list.toBookListModel
import com.james.rxjavamvvm.common.BaseResponse
import com.james.rxjavamvvm.domain.use_case.book_detail.GetBookDetailUseCase
import com.james.rxjavamvvm.domain.use_case.book_list.GetBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val getBookDetailUseCase: GetBookDetailUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _response: MutableLiveData<BaseResponse<List<BookListModel>>> = MutableLiveData()
    val response: LiveData<BaseResponse<List<BookListModel>>> get() = _response

    fun getBookList(q: String) {
        compositeDisposable.add(
            getBookListUseCase.execute(q)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    _response.postValue(BaseResponse.Loading())
                }
                .subscribe({
                    if (it.items.isEmpty()) {
                        _response.postValue(BaseResponse.NoItemResult())
                    } else {
                        val list = it.items.map {
                            it.toBookListModel()
                        }
                        _response.postValue(BaseResponse.Success(list))
                    }

                }, {
                    _response.postValue(BaseResponse.Error(it))
                }, {
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}