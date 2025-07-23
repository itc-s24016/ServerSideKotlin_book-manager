package jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.Rental

interface RentalRepository {
    fun startRental(rental: Rental)//レンタル開始
    fun endRental(bookId: Long)    //レンタル終了
}