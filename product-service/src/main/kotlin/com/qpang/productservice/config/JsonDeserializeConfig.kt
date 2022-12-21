package com.qpang.productservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.converter.StringJsonMessageConverter

@Configuration
class JsonDeserializeConfig {
    @Bean
    fun jsonConverter(): StringJsonMessageConverter = StringJsonMessageConverter()
}