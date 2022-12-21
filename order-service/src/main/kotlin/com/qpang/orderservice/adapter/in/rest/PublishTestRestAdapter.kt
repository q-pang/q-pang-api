package com.qpang.orderservice.adapter.`in`.rest

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PublishTestRestAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    @GetMapping("/order/publish")
    fun publish(): String {
        kafkaTemplate.send("q-pang", "testString")
        println("testString")
        return "success"
    }
}