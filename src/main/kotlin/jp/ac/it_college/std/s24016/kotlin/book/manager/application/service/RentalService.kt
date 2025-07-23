package jp.ac.it_college.std.s24016.kotlin.book.manager.application.service

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.Rental
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.RentalRepository
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.UserRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

//レンタル期間は２週間とする
private const val RENTAL_TERM_DAYS = 14L
//日本時間に設定
private val JST = TimeZone.of("Asia/Tokyo")

@Service
class RentalService (
    private val userRepository: UserRepository,//ユーザが存在しているか
    private val bookRepository: BookRepository,//指定された書籍IDが存在するか
    private val rentalRepository: RentalRepository
//    private val rentalRepository: RentalRepository

){

    @OptIn(ExperimentalTime::class)
    @Transactional
    fun startRental(bookId: Long, userId: Long){
        //ユーザが存在しているか
        userRepository.find(userId) ?: throw @ResponseStatus(HttpStatus.FORBIDDEN) object
            : IllegalArgumentException("該当するユーザが存在しません ID: ${userId}") {}

        //指定された書籍IDが存在するか
        val book =
            bookRepository.findWithRental(bookId) ?: throw @ResponseStatus(HttpStatus.NOT_FOUND) object
                : IllegalArgumentException("該当する書籍が存在しません ID: ${bookId}") {}

        //貸出中なら
        if (book.isRental) throw @ResponseStatus(HttpStatus.CONFLICT) object
            : IllegalArgumentException("貸出中の書籍です ID: ${bookId}") {}

        //Clock を OptIn している -by 2025/07/22-
        val now = Clock.System.now()
        val rentalDatetime = now.toLocalDateTime(JST)
        val returnDeadline = now.plus(RENTAL_TERM_DAYS.days).toLocalDateTime(JST)

        rentalRepository.startRental(
            Rental(bookId, userId, rentalDatetime, returnDeadline)
        )
    }
    //貸出する処理
    @Transactional
    fun endRental(bookId: Long, userId: Long){
        userRepository.find(userId)
            ?: throw @ResponseStatus(HttpStatus.FORBIDDEN) object
                : IllegalArgumentException("該当するユーザが存在しません ID: ${userId}") {}
       val book = bookRepository.findWithRental(bookId)
           ?: throw @ResponseStatus(HttpStatus.NOT_FOUND) object
               : IllegalArgumentException("該当する書籍が存在しません ID: ${bookId}") {}
        if (!book.isRental) throw @ResponseStatus(HttpStatus.BAD_REQUEST) object
                : IllegalArgumentException("貸出中の書籍ではありません ID: ${bookId}") {}
        if (book.rental?.userId != userId)
            throw @ResponseStatus(HttpStatus.FORBIDDEN) object
                : IllegalArgumentException("他のユーザへ貸出中の書籍です") {}
        rentalRepository.endRental(bookId)
    }
}