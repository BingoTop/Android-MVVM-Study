package com.james.rxjavamvvm.presentation.views.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.james.rxjavamvvm.R
import com.james.rxjavamvvm.common.Constants
import com.james.rxjavamvvm.common.BaseResponse
import com.james.rxjavamvvm.databinding.ActivityDetailBinding
import com.james.rxjavamvvm.presentation.viewmodel.DetailViewModel
import com.james.rxjavamvvm.presentation.views.utils.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var mLoadingDialog: LoadingDialog
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        mLoadingDialog = LoadingDialog(this)
        if (intent.hasExtra(Constants.BOOK_ID)) {
            val bookId = intent.getStringExtra(Constants.BOOK_ID)
            bookId?.let {
                getBookById(it)
            }
        }
    }

    private fun setUpToolBarText(title: String?) {
        title?.let {
            binding.detailCtl.title = it
        }
    }

    fun TextView.applyText(newText:String?){
        newText?.let {
            this.text = it
        }
    }

    private fun changeCollapsingLayoutStyle() {
        binding.detailCtl.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        binding.detailCtl.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
    }

    fun ImageView.buildGlide(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .fitCenter()
            .into(this)
    }

    private fun setImageView(imageUrl: String?) {
        imageUrl?.let {
            binding.detailIvBookImage.buildGlide(it)
        }
    }

    private fun initViewModel() {
        detailViewModel.response.observe(this, { response ->
            when (response) {
                is BaseResponse.Success -> {
                    val bookMediumImage = response.data?.imageLinks?.medium
                    setImageView(bookMediumImage)
                    val title = response.data?.title
                    setUpToolBarText(title)
                    changeCollapsingLayoutStyle()

                    val publisher = response.data?.publisher
                    val publishedDate = response.data?.publishedDate
                    val pageCount = response.data?.pageCount
                    val description = response.data?.description
                    val bookExtraInfo = "출판사: ${publisher}\n 출판일:${publishedDate}\n 페이지 수: ${pageCount}쪽"
                    binding.detailTvBookPublisher.applyText(bookExtraInfo)
                    binding.detailTvBookDesc.applyText(description)
                    mLoadingDialog.dismiss()
                }
                is BaseResponse.Error -> {
                    mLoadingDialog.dismiss()
                }
                is BaseResponse.Loading -> {
                    mLoadingDialog.show()
                }
            }
        })
    }

    private fun getBookById(id: String) {
        detailViewModel.getBookById(id)
    }

    fun String.replaceHtmlTags(): String {
        return this.replace("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "")
    }
}