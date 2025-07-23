package jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.from

import kotlinx.serialization.Serializable

@Serializable
data class RentalStartRequest(
    val bookId: Long,
)
