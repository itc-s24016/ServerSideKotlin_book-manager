package jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory //Javaのログライブラリ
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder

//Loggerに格納するためにKotlinのクラス情報のオブジェクトをjavaのクラス情報のオブジェクトに変換する
private val logger = LoggerFactory.getLogger(LoggingAdvice::class.java)

@Aspect
@Component
class LoggingAdvice {
    //controllerパッケージの中の全てのクラスの全てのメソッドが呼び出される直前にbeforeLogが機能する
    @Before("execution(* jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.controller..*.*(..))")
    //ログを出力する
    //これでなにかメソッドが呼び出されるときにログが表示されるようになる
    fun beforeLog(joinPoint: JoinPoint){
        val user = SecurityContextHolder.getContext().authentication.principal
        logger.info("Start: ${joinPoint.signature}")
        logger.info("Class: ${joinPoint.target.javaClass}")
        logger.info("Principal: ${user}")
        logger.info("Session: ${RequestContextHolder.getRequestAttributes()?.sessionId}")
    }

    //controllerパッケージの中の全てのクラスの全てのメソッドが呼び出された直後にが機能する
    @After("execution(* jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.controller..*.*(..))")
    fun afterLog(joinPoint: JoinPoint){
        val user = SecurityContextHolder.getContext().authentication.principal
        logger.info("End: ${joinPoint.signature}")
        logger.info("Principal: ${user}")
    }

    @AfterReturning("execution(* jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.controller..*.*(..))", returning = "returnValue")
    fun afterReturningLog(joinPoint: JoinPoint, returnValue: Any?){
        logger.info("End: ${joinPoint.signature} returnValue: ${returnValue}")
    }

    //エラーなど例外処理が起こった直後に機能する
    @AfterThrowing("execution(* jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.controller..*.*(..))", throwing = "e")
    fun afterThrowingLog(joinPoint: JoinPoint, e: Throwable){
        logger.error("Exception: ${e.javaClass} signature=${joinPoint.signature} message=${e.message}")
    }
}