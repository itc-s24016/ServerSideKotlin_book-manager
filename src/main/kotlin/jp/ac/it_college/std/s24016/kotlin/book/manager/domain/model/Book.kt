package jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model

import kotlinx.datetime.LocalDate

//書籍を表すデータクラス(データが欠けてほしくないので、Null許容型にはしない)
data class Book (
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
)