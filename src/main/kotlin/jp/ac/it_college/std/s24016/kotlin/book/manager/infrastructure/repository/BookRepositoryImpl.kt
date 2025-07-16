package jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.repository

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.Rental
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.BookMapper
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.BookWithRentalMapper
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.findByPrimaryKey
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.insert
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.select
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Repository
//domain.repository.BookRepository と名前がかぶるので、as で別名を付ける
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.record.BookWithRental as BookWithRentalRecord
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.record.Book as BookRecord

//インターフェースを使用する目印
@Repository
class BookRepositoryImpl (
    private val bookWithRentalMapper: BookWithRentalMapper,
    private val bookMapper: BookMapper
): BookRepository {
    override fun findAllWithRental(): List<BookWithRental> {
        //データベースからデータを取り出し、toModelの書き方にしている
        return bookWithRentalMapper.select().map(::toModel)
    }

    override fun findWithRental(bookId: Long): BookWithRental? {
        return bookWithRentalMapper.findByPrimaryKey(bookId)?.let(::toModel)
    }

    override fun register(book: Book) {
        bookMapper.insert(toRecord(book))
    }

    //レコードを受け取ってドメインに変換する(Java -> Kotlin)
    private fun toModel(record: BookWithRentalRecord) = record.run {
        //run に書くことで this を活用、変数名の短縮が可能になっている
        //強制的に非許容型にしている
        BookWithRental(
            Book(
                id!!,
                title!!,
                author!!,
                releaseDate!!.toKotlinLocalDate()
            ),
            //レンタルモデルを渡す(レンタル中の書籍のみ)
            //Java の LocalDateTime を Kotlin の LocalDateTime に変換
            userId?.let {
                Rental(
                    id!!,
                    userId!!,
                    rentalDatetime!!.toKotlinLocalDateTime(),
                    returnDeadline!!.toKotlinLocalDateTime()
                )
            }
        )
    }

    //レコードを受け取ってドメインに変換する(Kotlin -> Java)
    private fun toRecord(model: Book): BookRecord = model.run {
        BookRecord(
            id,
            title,
            author,
            releaseDate.toJavaLocalDate()
        )
    }
}
