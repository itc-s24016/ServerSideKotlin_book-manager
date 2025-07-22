package jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.repository

import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.User
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.repository.UserRepository
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.UserDynamicSqlSupport
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.UserMapper
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.selectByPrimaryKey
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.mapper.selectOne
import org.springframework.stereotype.Repository
//domain.model.User とかぶるので as で違う名前を付ける
import jp.ac.it_college.std.s24016.kotlin.book.manager.infrastructure.database.record.User as UserRecord

@Repository
class UserRepositoryImpl(
    private val mapper: UserMapper
) : UserRepository {
    override fun find(email: String): User? {
        val record = mapper.selectOne {
            where {
                UserDynamicSqlSupport.email isEqualTo email
            }
        }
        //モデルに変換して返す
        return record?.let(::toModel)
    }

    override fun find(id: Long): User? {
        return mapper.selectByPrimaryKey(id)?.let(::toModel)
    }

    //ここでドメインモデルに変換する
    private fun toModel(record: UserRecord): User = record.run {
        User(
            id!!,
            email!!,
            password!!,
            name!!,
            roleType!!
        )
    }
}