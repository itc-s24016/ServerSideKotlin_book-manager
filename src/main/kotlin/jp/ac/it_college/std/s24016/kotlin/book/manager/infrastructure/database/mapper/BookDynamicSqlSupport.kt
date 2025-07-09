/*
 * Auto-generated file. Created by MyBatis Generator
 */
package jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper

import java.sql.JDBCType
import java.util.Date
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object BookDynamicSqlSupport {
    val book = Book()

    val id = book.id

    val title = book.title

    val author = book.author

    val releaseDate = book.releaseDate

    class Book : AliasableSqlTable<Book>("book", ::Book) {
        val id = column<Long>(name = "id", jdbcType = JDBCType.BIGINT)

        val title = column<String>(name = "title", jdbcType = JDBCType.VARCHAR)

        val author = column<String>(name = "author", jdbcType = JDBCType.VARCHAR)

        val releaseDate = column<Date>(name = "release_date", jdbcType = JDBCType.DATE)
    }
}