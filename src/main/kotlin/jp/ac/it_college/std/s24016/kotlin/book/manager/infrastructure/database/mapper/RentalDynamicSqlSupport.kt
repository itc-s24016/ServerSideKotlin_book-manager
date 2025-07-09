/*
 * Auto-generated file. Created by MyBatis Generator
 */
package jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper

import java.sql.JDBCType
import java.util.Date
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object RentalDynamicSqlSupport {
    val rental = Rental()

    val bookId = rental.bookId

    val userId = rental.userId

    val rentalDatetime = rental.rentalDatetime

    class Rental : AliasableSqlTable<Rental>("rental", ::Rental) {
        val bookId = column<Long>(name = "book_id", jdbcType = JDBCType.BIGINT)

        val userId = column<Long>(name = "user_id", jdbcType = JDBCType.BIGINT)

        val rentalDatetime = column<Date>(name = "rental_datetime", jdbcType = JDBCType.TIMESTAMP)
    }
}