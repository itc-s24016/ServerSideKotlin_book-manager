package jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.BookWithRental

//　関数を定義したインターフェース
interface BookRepository {
    fun findAllWithRental(): List<BookWithRental>
}