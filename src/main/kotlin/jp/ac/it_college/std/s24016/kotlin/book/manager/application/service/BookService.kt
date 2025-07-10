package jp.ac.it_college.std.s24016.kotlin.book.manager.application.service
//データベースからデータを取得、一覧表示用に提供するクラス
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service
//このクラスがビジネスロジックをを扱う「サービス層」のコンポーネントであることを指す
@Service
class BookService (
    private val bookRepository: BookRepository
){
    fun getList(): List<BookWithRental> {
        return bookRepository.findAllWithRental()
    }
}
