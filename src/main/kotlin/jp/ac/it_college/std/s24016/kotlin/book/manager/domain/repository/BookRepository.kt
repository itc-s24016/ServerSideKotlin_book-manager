package jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.BookWithRental
import kotlinx.datetime.LocalDate

//　関数を定義したインターフェース
interface BookRepository {
    fun findAllWithRental(): List<BookWithRental>

    fun findWithRental(bookId: Long): BookWithRental?

    fun register(book: Book)

    fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?)

    fun delete(id: Long)
}