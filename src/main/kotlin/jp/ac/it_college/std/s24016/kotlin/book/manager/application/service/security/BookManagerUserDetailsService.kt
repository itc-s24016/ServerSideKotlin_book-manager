package jp.ac.it_college.std.s24016.kotlin.book.manager.application.service.security

import jp.ac.it_college.std.s24016.kotlin.book.manager.application.service.AuthenticationService
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model.User
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.types.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class BookManagerUserDetailsService (
    private val authenticationService: AuthenticationService
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        //Usernameが取得できなかった場合
        username ?: throw UsernameNotFoundException("メールアドレスかパスワードが違います")

        val user = authenticationService.findUser(username)//実際にはメールアドレスが入っている
            ?: throw UsernameNotFoundException("メールアドレスかパスワードが違います")
        return BookManagerUserDetails(user)
    }
}

data class BookManagerUserDetails(
    val id: Long,
    val email: String,
    private val password: String, //Java でコンパイルしたときにゲッターの名前 getPassword() が被ってしまうので、private にする
    val roleType: RoleType
) : UserDetails {

    //セカンダリコンストラクタ
    constructor(user: User) : this(user.id, user.email, user.password, user.roleType)
    //権限　RoleTypeの情報を含むデータを返す
    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return AuthorityUtils.createAuthorityList(listOf(roleType.name))
    }
    //
    override fun getPassword(): String? {
        return password
    }
    override fun getUsername(): String? {
        return email
    }
}