package jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper

//MyBatis を使用して、データベースからデータを取得するためのマッパーインターフェースを定義している
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.author
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.book
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.id
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.releaseDate
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.title
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.rental
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.rentalDatetime
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.returnDeadline
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.userId
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.record.BookWithRental
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.type.JdbcType
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.util.SqlProviderAdapter
import org.mybatis.dynamic.sql.util.kotlin.SelectCompleter
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectList

@Mapper
interface BookWithRentalMapper {
    @SelectProvider(type = SqlProviderAdapter::class, method = "select")
    @Results(
        id = "BookWithRentalResult",
        value = [
            Result(column = "id",              property = "id",             jdbcType = JdbcType.BIGINT, id = true),
            Result(column = "title",           property = "title",          jdbcType = JdbcType.VARCHAR),
            Result(column = "author",          property = "author",         jdbcType = JdbcType.VARCHAR),
            Result(column = "release_date",    property = "releaseDate",    jdbcType = JdbcType.DATE),
            Result(column = "user_id",         property = "userId",         jdbcType = JdbcType.BIGINT),
            Result(column = "rental_datetime", property = "rentalDatetime", jdbcType = JdbcType.TIMESTAMP),
            Result(column = "return_deadline", property = "returnDeadline", jdbcType = JdbcType.TIMESTAMP),
        ] //KotlinのスネークケースをJavaで対応させるためにキャメルケースにしている
    )
    fun selectMany(selectStatementProvider: SelectStatementProvider): List<BookWithRental>
}

private val columnList = listOf(
    id,
    title,
    author,
    releaseDate,
    userId,
    rentalDatetime,
    returnDeadline
)

//後で検索条件を追加できるような select 関数
fun BookWithRentalMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, book) {
        leftJoin(rental, "r"){
            on(id) equalTo rental.bookId
        }
        run(completer)
    }

//select * from book; の条件　
fun BookWithRentalMapper.select() = select {}