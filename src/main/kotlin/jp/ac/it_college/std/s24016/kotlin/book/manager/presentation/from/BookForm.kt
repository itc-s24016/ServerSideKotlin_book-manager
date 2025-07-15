package jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.from

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.Rental
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

//書籍データのレスポンスモデルを定義する
@Serializable
data class GetBookListResponse(
    val bookList: List<BookInfo>
)

@Serializable
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

@Serializable
data class GetBookDetailResponse(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate,
    val rentalInfo : RentalInfo?
){
    constructor(model: BookWithRental) : this(
        model.book.id,
        model.book.title,
        model.book.author,
        model.book.releaseDate,
        model.rental?.run(::RentalInfo)
    )
}

@Serializable
data class RentalInfo(
    val userId: Long,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime,
){
    constructor(rental: Rental): this(
        rental.userId,
        rental.rentalDatetime,
        rental.returnDeadline,
    )
}