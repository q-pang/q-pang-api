package com.qpang.productservice.adapter.`in`.event

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ConsumerTestEventAdapter {
    @KafkaListener(topics = ["q-pang"])
    fun consume(message: String) {
        println(message)
    }
}