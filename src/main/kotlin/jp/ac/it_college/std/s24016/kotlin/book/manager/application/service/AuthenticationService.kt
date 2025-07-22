package jp.ac.it_college.std.s24016.kotlin.book.manager.application.service

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.User
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthenticationService (
    private val userRepository: UserRepository
){
    fun findUser(email: String): User? {
        return userRepository.find(email)
    }
}