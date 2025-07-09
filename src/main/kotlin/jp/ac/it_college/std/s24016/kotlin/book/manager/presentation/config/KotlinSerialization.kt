package jp.ac.it_college.std.s24016.kotlin.book.manager.presentation.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter

//Spring Boot の設定ファイルだと認識される
@Configuration
class KotlinSerialization {

    @OptIn(ExperimentalSerializationApi::class)
    @Bean
    fun messageConverter(): KotlinSerializationJsonHttpMessageConverter{
        val json = Json {
            ignoreUnknownKeys = true //想定していないキーは無視する指示
            explicitNulls = false //「明示的な」Nullは必要ない、入れないでという指示　この指示は結構デフォルトで使われるらしい
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
        return KotlinSerializationJsonHttpMessageConverter(json)
    }
}