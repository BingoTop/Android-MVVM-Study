package com.james.rxjavamvvm.presentation.views.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.james.rxjavamvvm.R
import com.james.rxjavamvvm.common.Constants
import com.james.rxjavamvvm.domain.model.BookListModel
import com.james.rxjavamvvm.common.BaseResponse
import com.james.rxjavamvvm.presentation.viewmodel.MainViewModel
import com.james.rxjavamvvm.presentation.views.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    @Named("searchAdapter")
    lateinit var searchAdapter: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mDisposable = getEditTextObservable()
            .debounce(500L, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getEditTextObserver())
        compositeDisposable.add(mDisposable)
        initAdapter()
        initViewModel()
    }

    private fun initAdapter() {
        main_rv.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        searchAdapter.mPublishSubject.subscribe {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(Constants.BOOK_ID,it.id)
            startActivity(intent)
        }
    }

    private fun getEditTextObservable(): Observable<CharSequence> {
        return Observable.create { e ->
            main_et.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let {
                        if (it.length < 2) {
                            if (!searchAdapter.itemCount.equals(0)) {
                                searchAdapter.clearBookList()
                            }
                        } else {
                            e.onNext(it)
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    private fun getEditTextObserver(): DisposableObserver<CharSequence> {
        return object : DisposableObserver<CharSequence>() {
            override fun onNext(t: CharSequence) {
                mainViewModel.getBookList(t.toString())
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }
        }
    }

    private fun initViewModel() {
        mainViewModel.response.observe(this, { response ->
            when (response) {
                is BaseResponse.Success -> {
                    response.data?.let {
                        searchAdapter.setBookList(it as ArrayList<BookListModel>)
                        main_frm.visibility = View.GONE
                    }
                    Log.d("TAG", "initViewModel: Success")
                }
                is BaseResponse.Error -> {
                    Log.d("TAG", "initViewModel: Error ${response.throwable?.message}")
                    main_frm.visibility = View.GONE
                }
                is BaseResponse.NoItemResult -> {
                    Log.d("TAG", "initViewModel: NoItemResult")
                }
                is BaseResponse.Loaded -> {
                    Log.d("TAG", "initViewModel: Loaded")
                }
                is BaseResponse.Loading -> {
                    main_frm.visibility = View.VISIBLE
                    Log.d("TAG", "initViewModel: Loading")
                }
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}