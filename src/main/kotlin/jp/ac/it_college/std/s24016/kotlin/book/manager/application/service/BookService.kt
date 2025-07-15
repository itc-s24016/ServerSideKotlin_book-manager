package jp.ac.it_college.std.s24016.kotlin.book.manager.application.service
//データベースからデータを取得、一覧表示用に提供するクラス
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.BookRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus

//このクラスがビジネスロジックを扱う「サービス層」のコンポーネントであることを指す
@Service
class BookService (
    private val bookRepository: BookRepository
){
    fun getList(): List<BookWithRental> {
        return bookRepository.findAllWithRental()
    }

    fun getDetail(bookId: Long): BookWithRental {
        return bookRepository.findWithRental(bookId)
            ?: throw @ResponseStatus(HttpStatus.NOT_FOUND) object
                : IllegalArgumentException("存在しない書籍ID: ${bookId}") {}
    }
}
