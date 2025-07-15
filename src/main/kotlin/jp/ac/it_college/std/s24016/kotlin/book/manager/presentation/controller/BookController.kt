package jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.controller

import jp.ac.it_college.std.s24016.kotlin.book.manager.application.service.BookService
import jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.from.BookInfo
import jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.from.GetBookDetailResponse
import jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.from.GetBookListResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/book")
//セキュリティ機能を導入するときに必要
@CrossOrigin
class BookController (
    private val bookService: BookService
){
    @GetMapping("/list") //BookInfoのリストを返す
    fun getList(): GetBookListResponse {
        val bookList = bookService.getList().map(::BookInfo)
        return GetBookListResponse(bookList)
    }
     @GetMapping("/detail/{bookId}")
     fun getDetail(@PathVariable bookId: Long): GetBookDetailResponse {
         val book = bookService.getDetail(bookId)
         return GetBookDetailResponse(book)
     }
}