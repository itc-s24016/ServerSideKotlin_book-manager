package jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model

import kotlinx.datetime.LocalDateTime

//貸出情報を表すデータクラス(データが欠けてほしくないので、Null許容型にはしない)
data class Rental (
    val bookId: Long,
    val userId: Long,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime
)