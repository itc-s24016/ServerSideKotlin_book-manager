package jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jp.ac.it_college.std.s24016.kotlin.book.manager.application.service.AuthenticationService
import jp.ac.it_college.std.s24016.kotlin.book.manager.application.service.security.BookManagerUserDetailsService
import jp.ac.it_college.std.s24016.kotlin.book.manager.domain.types.RoleType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration // 何かの設定を変更するクラスだと示す
@EnableWebSecurity //何) HTTPに関するセキュリティーの設定
class WebSecurityConfig(
    private val authenticationService: AuthenticationService
) {
    @Bean //HTTP関連の基本的な設定用のメソッドと示す
    @Order(1) //優先順位は1番目
    fun configure(http: HttpSecurity) : SecurityFilterChain {
        //http {} を Tab を使わずに打ち、
        //import org.springframework.security.config.annotation.web.invoke を手打ち
        http {
            //どこ宛のアクセスを許可するか = パスそれぞれの設定
            authorizeHttpRequests {
                // 全て無条件で許可するパスの設定
                authorize("/login", permitAll)
                // 管理者権限がある人だけを許可するパスの設定
                authorize("/admin/**", hasAuthority(RoleType.ADMIN.name))
                // それ以外のパス -> anyRequest
                // ログインが必要 -> authenticated
                authorize(anyRequest, authenticated)
            }
            //ログイン機能の設定
            formLogin {
                loginProcessingUrl = "/login"//ログイン用のURLを指定
                usernameParameter = "email"  //email を username として扱うように変更
                passwordParameter = "pass"
                authenticationSuccessHandler = AuthenticationHandler //ログインに成功した場合
                authenticationFailureHandler = AuthenticationHandler //ログインに失敗した場合
            }
            //クロスサイトリクエストフォージェリという攻撃を対策する機能をあえてOFFにする
            csrf {
                disable() //無効
            }
            //例外処理
            exceptionHandling {
                authenticationEntryPoint = AuthenticationHandler
                accessDeniedHandler = AuthenticationHandler
            }
        }
        return http.build() //設定を終え、適用するためのオブジェクトを構築・生成する
    }

    @Bean
    fun userDetailsService(): UserDetailsService =
        BookManagerUserDetailsService(authenticationService)

    @Bean
    //パスワードに関する設定
    fun passwordEncoder(): PasswordEncoder =
        //Argon2で計算に必要なパラメータを設定
        Argon2PasswordEncoder(16, 32, 1, 19456, 2)

    @Bean
    /* フロントエンドとバックエンドが別々で作られていた場合、IPアドレスやドメイン名が異なることがある
       そのような状態を「オリジンが異なる」といい、デフォでは「同一オリジンポリシー」という仕様でブロックされてしまう */
    //異なるウェブサイト同士( 異なるオリジン同士 )でデータのやり取りをするための仕様 -> cors の設定
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowedMethods = listOf(CorsConfiguration.ALL)
            allowedHeaders = listOf(CorsConfiguration.ALL)
            allowedOrigins = listOf(
                "http://localhost:3000",
                "http://127.0.0.1:3000"
            )
            allowCredentials = true
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }
}

object AuthenticationHandler :
        AuthenticationSuccessHandler, //認証が成功した時
    AuthenticationFailureHandler, //認証が失敗した時
    AuthenticationEntryPoint, //未認証なので認証して
    AccessDeniedHandler //アクセスが拒否された(権限が無い等)
        {

            override fun onAuthenticationSuccess(
                request: HttpServletRequest?,
                response: HttpServletResponse?,
                authentication: Authentication?
            ) {
                response?.status = HttpServletResponse.SC_OK //認証OKなら200を返す
            }

            override fun onAuthenticationFailure(
                request: HttpServletRequest?,
                response: HttpServletResponse?,
                exception: AuthenticationException?
            ) {
                response?.status = HttpServletResponse.SC_UNAUTHORIZED//認証失敗なら401を返す
            }

            override fun commence(
                request: HttpServletRequest?,
                response: HttpServletResponse?,
                authException: AuthenticationException?
            ) {
                response?.status = HttpServletResponse.SC_UNAUTHORIZED //未認証でも401を返す
            }

            override fun handle(
                request: HttpServletRequest?,
                response: HttpServletResponse?,
                accessDeniedException: AccessDeniedException?
            ) {
                response?.status = HttpServletResponse.SC_FORBIDDEN //権限がなかった時は403を返す
            }

        }