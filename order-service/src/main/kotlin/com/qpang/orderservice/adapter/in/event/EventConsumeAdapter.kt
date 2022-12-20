package com.qpang.orderservice.adapter.`in`.event

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class EventConsumeAdapter {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["exam-topic"], groupId = "foo")
    fun consume(message: String) {
        log.info("Consumed message : $message")
    }
}