package jp.ac.it_college.std.s24016.kotlin.book.manager.application.service

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.BookRepository
import kotlinx.datetime.LocalDate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ResponseStatus

@Service
class AdminBookService (
    private val bookRepository: BookRepository
){
    //SQLでやったトランザクション制御をいい感じで行う
    @Transactional
    fun register(book: Book){
        bookRepository.findWithRental(book.id)?.let{
            //すでに同じIDの書籍が存在したらエラーを返し、処理をなかったことにする(rollback)
            /*
            400:明確な理由はわからないけど、リクエスト内容が間違っているのでダメ
            404:リクエスト先が見つからないのでダメ
            409:リクエストが現在のリソースと競合するのでダメ
            */
            throw @ResponseStatus(HttpStatus.CONFLICT) object
                :IllegalStateException("すでに存在する書籍ID: ${book.id}") {}
        }
        //let に引っ掛からない = 書籍が登録されていないので新規登録可能
        bookRepository.register(book)//bookを登録
    }

    @Transactional
    fun update(bookId: Long, title: String?, author: String?, releaseDate: LocalDate?){
        bookRepository.findWithRental(bookId)
            ?: throw @ResponseStatus(HttpStatus.NOT_FOUND) object
                : IllegalStateException("存在しない書籍ID: ${bookId}") {}
        bookRepository.update(bookId, title, author, releaseDate)
    }
}