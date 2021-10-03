package com.james.rxjavamvvm.presentation.views.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.james.rxjavamvvm.R
import com.james.rxjavamvvm.domain.model.BookListModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class SearchAdapter(private val context: Context) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    val mPublishSubject: PublishSubject<BookListModel> = PublishSubject.create()
    private var bookList = arrayListOf<BookListModel>()

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookName = itemView.findViewById<TextView>(R.id.book_item_tv_name)
        val bookDesc = itemView.findViewById<TextView>(R.id.book_item_tv_desc)
        val bookImage = itemView.findViewById<ImageView>(R.id.book_item_iv)

        fun bind(book: BookListModel) {
            bookName.text = book.title
            bookDesc.text = book.description
            val thumbnailWidth = context.resources.getDimension(R.dimen.book_image_width).toInt()
            val thumbnailHeight = context.resources.getDimension(R.dimen.book_image_height).toInt()
            book.imageLinks?.smallThumbnail?.let {
                Glide.with(bookImage)
                    .load(book.imageLinks.smallThumbnail)
                    .override(
                        thumbnailWidth,
                        thumbnailHeight
                    )
                    .fitCenter()
                    .into(bookImage)
            }
        }

        fun getClickObserver(book: BookListModel): Observable<BookListModel> {
            return Observable.create { e ->
                itemView.setOnClickListener {
                    e.onNext(book)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(bookList[position])
        holder.getClickObserver(bookList[position])
            .debounce(500L,TimeUnit.MILLISECONDS)
            .subscribe(mPublishSubject)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun setBookList(newBookList: ArrayList<BookListModel>) {
        if (bookList.size > 0) {
            bookList.clear()
        }
        this.bookList = newBookList
        notifyDataSetChanged()
    }

    fun clearBookList() {
        this.bookList.clear()
    }
}