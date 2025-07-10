package jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.from

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.BookWithRental
//書籍データのレスポンスモデルを定義する
data class GetBookListResponse(
    val bookList: List<BookInfo>
)

data class BookInfo(
    val id: Long,
    val title: String,
    val author: String,
    val isRental: Boolean,
){
    constructor(model: BookWithRental) : this(
        model.book.id,
        model.book.title,
        model.book.author,
        model.isRental
    )
}