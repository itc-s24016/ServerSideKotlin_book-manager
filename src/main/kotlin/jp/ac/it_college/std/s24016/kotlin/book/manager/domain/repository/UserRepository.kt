package jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.User

interface UserRepository {
    //登録されていないアドレスでログインする可能性もあるのでNULL許容型にする
    fun find(email: String): User?

    fun find(id: Long): User?
}