package com.orders.mongo.adapter

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.util.*

@Configuration
@EnableMongoRepositories
@EntityScan
@PropertySource("classpath:ca-orders-mongo-adapter.properties")
class SpringMongoConfiguration {

    @Bean
    fun customConversions(): MongoCustomConversions {
        return MongoCustomConversions(listOf<Converter<*,*>>(UuidWriter.INSTANCE, UuidReader.INSTANCE))
    }

    @WritingConverter
    enum class UuidWriter : Converter<UUID, String> {
        INSTANCE;

        override fun convert(source: UUID): String {
            return source.toString()
        }
    }

    @ReadingConverter
    enum class UuidReader : Converter<String, UUID> {
        INSTANCE;

        override fun convert(source: String): UUID? {
            return UUID.fromString(source)
        }
    }
}