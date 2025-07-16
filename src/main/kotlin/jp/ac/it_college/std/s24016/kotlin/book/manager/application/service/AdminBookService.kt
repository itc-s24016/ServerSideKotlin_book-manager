package jp.ac.it_college.std.s24016.kotlin.book.manager.application.service

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminBookService (
    private val bookRepository: BookRepository
){
    //SQLでやったトランザクション制御をいい感じで行う
    @Transactional
    fun register(book: Book){
        bookRepository.findWithRental(book.id)?.let{
            //すでに同じIDの書籍が存在したらエラーを返し、処理をなかったことにする(rollback)

        }
    }

}